  public double integrate(final FirstOrderDifferentialEquations equations,
                          final double t0, final double[] y0,
                          final double t, final double[] y)
  throws DerivativeException, IntegratorException {
    sanityChecks(equations, t0, y0, t, y);
    setEquations(equations);
    resetEvaluations();
    final boolean forward = t > t0;
    final int stages = c.length + 1;
    if (y != y0) {
      System.arraycopy(y0, 0, y, 0, y0.length);
    }
    final double[][] yDotK = new double[stages][y0.length];
    final double[] yTmp = new double[y0.length];
    AbstractStepInterpolator interpolator;
    if (requiresDenseOutput() || (! eventsHandlersManager.isEmpty())) {
      final RungeKuttaStepInterpolator rki = (RungeKuttaStepInterpolator) prototype.copy();
      rki.reinitialize(this, yTmp, yDotK, forward);
      interpolator = rki;
    } else {
      interpolator = new DummyStepInterpolator(yTmp, forward);
    }
    interpolator.storeTime(t0);
    stepStart         = t0;
    double  hNew      = 0;
    boolean firstTime = true;
    for (StepHandler handler : stepHandlers) {
        handler.reset();
    }
    CombinedEventsManager manager = addEndTimeChecker(t0, t, eventsHandlersManager);
    boolean lastStep = false;
    while (!lastStep) {
      interpolator.shift();
      double error = 0;
      for (boolean loop = true; loop;) {
        if (firstTime || !fsal) {
          computeDerivatives(stepStart, y, yDotK[0]);
        }
        if (firstTime) {
         final double[] scale;
          if (vecAbsoluteTolerance == null) {
              scale = new double[y0.length];
              java.util.Arrays.fill(scale, scalAbsoluteTolerance);
            } else {
              scale = vecAbsoluteTolerance.clone();
            }
          hNew = initializeStep(equations, forward, getOrder(), scale,
                                stepStart, y, yDotK[0], yTmp, yDotK[1]);
          firstTime = false;
        }
        stepSize = hNew;
        for (int k = 1; k < stages; ++k) {
          for (int j = 0; j < y0.length; ++j) {
            double sum = a[k-1][0] * yDotK[0][j];
            for (int l = 1; l < k; ++l) {
              sum += a[k-1][l] * yDotK[l][j];
            }
            yTmp[j] = y[j] + stepSize * sum;
          }
          computeDerivatives(stepStart + c[k-1] * stepSize, yTmp, yDotK[k]);
        }
        for (int j = 0; j < y0.length; ++j) {
          double sum    = b[0] * yDotK[0][j];
          for (int l = 1; l < stages; ++l) {
            sum    += b[l] * yDotK[l][j];
          }
          yTmp[j] = y[j] + stepSize * sum;
        }
        error = estimateError(yDotK, y, yTmp, stepSize);
        if (error <= 1.0) {
          interpolator.storeTime(stepStart + stepSize);
          if (manager.evaluateStep(interpolator)) {
              final double dt = manager.getEventTime() - stepStart;
              if (Math.abs(dt) <= Math.ulp(stepStart)) {
                  loop = false;
              } else {
                  hNew = dt;
              }
          } else {
            loop = false;
          }
        } else {
          final double factor =
              Math.min(maxGrowth,
                       Math.max(minReduction, safety * Math.pow(error, exp)));
          hNew = filterStep(stepSize * factor, forward, false);
        }
      }
      final double nextStep = stepStart + stepSize;
      System.arraycopy(yTmp, 0, y, 0, y0.length);
      manager.stepAccepted(nextStep, y);
      lastStep = manager.stop();
      interpolator.storeTime(nextStep);
      for (StepHandler handler : stepHandlers) {
          handler.handleStep(interpolator, lastStep);
      }
      stepStart = nextStep;
      if (fsal) {
        System.arraycopy(yDotK[stages - 1], 0, yDotK[0], 0, y0.length);
      }
      if (manager.reset(stepStart, y) && ! lastStep) {
        computeDerivatives(stepStart, y, yDotK[0]);
      }
      if (! lastStep) {
          stepSize = filterStep(stepSize, forward, true);
        final double factor = Math.min(maxGrowth,
                                       Math.max(minReduction,
                                                safety * Math.pow(error, exp)));
        final double  scaledH    = stepSize * factor;
        final double  nextT      = stepStart + scaledH;
        final boolean nextIsLast = forward ? (nextT >= t) : (nextT <= t);
        hNew = filterStep(scaledH, forward, nextIsLast);
      }
    }
    final double stopTime = stepStart;
    resetInternalState();
    return stopTime;
  }
