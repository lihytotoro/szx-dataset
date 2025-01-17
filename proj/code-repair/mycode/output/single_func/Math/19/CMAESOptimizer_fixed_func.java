    private void checkParameters() {
        final double[] init = getStartPoint();
        final double[] lB = getLowerBound();
        final double[] uB = getUpperBound();
        boolean hasFiniteBounds = false;
        for (int i = 0; i < lB.length; i++) {
            if (!Double.isInfinite(lB[i]) ||
                !Double.isInfinite(uB[i])) {
                hasFiniteBounds = true;
                break;
            }
        }
        boolean hasInfiniteBounds = false;
        if (hasFiniteBounds) {
            for (int i = 0; i < lB.length; i++) {
                if (Double.isInfinite(lB[i]) ||
                    Double.isInfinite(uB[i])) {
                    hasInfiniteBounds = true;
                    break;
                }
            }
            if (hasInfiniteBounds) {
                throw new MathUnsupportedOperationException();
            } else {
                boundaries = new double[2][];
                boundaries[0] = lB;
                boundaries[1] = uB;
               if (boundaries[0].length != boundaries[1].length) {
                    throw new DimensionMismatchException(boundaries[0].length, boundaries[1].length);
                }
            }
        } else {
            boundaries = null;
        }
        if (inputSigma != null) {
            if (inputSigma.length != init.length) {
                throw new DimensionMismatchException(inputSigma.length, init.length);
            }
            for (int i = 0; i < init.length; i++) {
                if (inputSigma[i] < 0) {
                    throw new NotPositiveException(inputSigma[i]);
                }
                if (boundaries != null) {
                    if (inputSigma[i] > boundaries[1][i] - boundaries[0][i]) {
                        throw new OutOfRangeException(inputSigma[i], 0, boundaries[1][i] - boundaries[0][i]);
                    }
                }
            }
        }
    }
