    protected VectorialPointValuePair doOptimize()
        throws FunctionEvaluationException, OptimizationException, IllegalArgumentException {
        solvedCols  = Math.min(rows, cols);
        diagR       = new double[cols];
        jacNorm     = new double[cols];
        beta        = new double[cols];
        permutation = new int[cols];
        lmDir       = new double[cols];
        double   delta   = 0;
        double   xNorm   = 0;
        double[] diag    = new double[cols];
        double[] oldX    = new double[cols];
        double[] oldRes  = new double[rows];
                }
                if ((Math.abs(actRed) <= 2.2204e-16) && (preRed <= 2.2204e-16) && (ratio <= 2.0)) {
                    throw new OptimizationException(LocalizedFormats.TOO_SMALL_COST_RELATIVE_TOLERANCE,
                            costRelativeTolerance);
                } else if (delta <= 2.2204e-16 * xNorm) {
                    throw new OptimizationException(LocalizedFormats.TOO_SMALL_PARAMETERS_RELATIVE_TOLERANCE,
                            parRelativeTolerance);
                } else if (maxCosine <= 2.2204e-16)  {
                    throw new OptimizationException(LocalizedFormats.TOO_SMALL_ORTHOGONALITY_TOLERANCE,
                            orthoTolerance);
                }
            }
        }
    }
