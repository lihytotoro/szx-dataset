  public void integrate(final ExpandableStatefulODE equations, final double t)
      throws MathIllegalStateException, MathIllegalArgumentException {
    sanityChecks(equations, t);
    setEquations(equations);
    final boolean forward = t > equations.getTime();
    final double[] y0  = equations.getCompleteState();
    final double[] y = y0.clone();
    final int stages = c.length + 1;
    final double[][] yDotK = new double[stages][y.length];
    final double[] yTmp    = y0.clone();
    final double[] yDotTmp = new double[y.length];
    final RungeKuttaStepInterpolator interpolator = (RungeKuttaStepInterpolator) prototype.copy();
    interpolator.reinitialize(this, yTmp, yDotK, forward,
                              equations.getPrimaryMapper(), equations.getSecondaryMappers());
    interpolator.storeTime(equations.getTime());
    stepStart         = equations.getTime();
    double  hNew      = 0;
    boolean firstTime = true;
    initIntegration(equations.getTime(), y0, t);
    isLastStep = false;
    do {
      interpolator.shift();
      double error = 10;
      while (error >= 1.0) {
        if (firstTime || !fsal) {
          computeDerivatives(stepStart, y, yDotK[0]);
        }
        if (firstTime) {
          final double[] scale = new double[mainSetDimension];
          if (vecAbsoluteTolerance == null) {
              for (int i = 0; i < scale.length; ++i) {
                scale[i] = scalAbsoluteTolerance + scalRelativeTolerance * FastMath.abs(y[i]);
              }
          } else {
              for (int i = 0; i < scale.length; ++i) {
                scale[i] = vecAbsoluteTolerance[i] + vecRelativeTolerance[i] * FastMath.abs(y[i]);
              }
          }
          hNew = initializeStep(forward, getOrder(), scale,
                                stepStart, y, yDotK[0], yTmp, yDotK[1]);
          firstTime = false;
        }
        stepSize = hNew;
       System.arraycopy(y, 0, yTmp, 0, y.length);
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
        if (error >= 1.0) {
          final double factor =
              FastMath.min(maxGrowth,
                           FastMath.max(minReduction, safety * FastMath.pow(error, exp)));
          hNew = filterStep(stepSize * factor, forward, false);
        }
      }
      interpolator.storeTime(stepStart + stepSize);
      System.arraycopy(yTmp, 0, y, 0, y0.length);
      System.arraycopy(yDotK[stages - 1], 0, yDotTmp, 0, y0.length);
      stepStart = acceptStep(interpolator, y, yDotTmp, t);
      System.arraycopy(y, 0, yTmp, 0, y.length);
      if (!isLastStep) {
          interpolator.storeTime(stepStart);
          if (fsal) {
              System.arraycopy(yDotTmp, 0, yDotK[0], 0, y0.length);
          }
          final double factor =
              FastMath.min(maxGrowth, FastMath.max(minReduction, safety * FastMath.pow(error, exp)));
          final double  scaledH    = stepSize * factor;
          final double  nextT      = stepStart + scaledH;
          final boolean nextIsLast = forward ? (nextT >= t) : (nextT <= t);
          hNew = filterStep(scaledH, forward, nextIsLast);
          final double  filteredNextT      = stepStart + hNew;
          final boolean filteredNextIsLast = forward ? (filteredNextT >= t) : (filteredNextT <= t);
          if (filteredNextIsLast) {
              hNew = t - stepStart;
          }
      }
    } while (!isLastStep);
    equations.setTime(stepStart);
    equations.setCompleteState(y);
    resetInternalState();
  }
