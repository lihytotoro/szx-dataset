    protected double doSolve() {
        final double[] x = new double[maximalOrder + 1];
        final double[] y = new double[maximalOrder + 1];
        x[0] = getMin();
        x[1] = getStartValue();
        x[2] = getMax();
        verifySequence(x[0], x[1], x[2]);
        y[1] = computeObjectiveValue(x[1]);
        if (Precision.equals(y[1], 0.0, 1)) {
            return x[1];
        }
        y[0] = computeObjectiveValue(x[0]);
        if (Precision.equals(y[0], 0.0, 1)) {
            return x[0];
        }
        int nbPoints;
        int signChangeIndex;
        if (y[0] * y[1] < 0) {
            nbPoints        = 2;
            signChangeIndex = 1;
        } else {
            y[2] = computeObjectiveValue(x[2]);
            if (Precision.equals(y[2], 0.0, 1)) {
                return x[2];
            }
            if (y[1] * y[2] < 0) {
                nbPoints        = 3;
                signChangeIndex = 2;
            } else {
                throw new NoBracketingException(x[0], x[2], y[0], y[2]);
            }
        }
        final double[] tmpX = new double[x.length];
        double xA    = x[signChangeIndex - 1];
        double yA    = y[signChangeIndex - 1];
        double absYA = FastMath.abs(yA);
        int agingA   = 0;
        double xB    = x[signChangeIndex];
        double yB    = y[signChangeIndex];
        double absYB = FastMath.abs(yB);
        int agingB   = 0;
        while (true) {
            final double xTol = getAbsoluteAccuracy() +
                                getRelativeAccuracy() * FastMath.max(FastMath.abs(xA), FastMath.abs(xB));
            if (((xB - xA) <= xTol) || (FastMath.max(absYA, absYB) < getFunctionValueAccuracy())) {
                switch (allowed) {
                case ANY_SIDE :
                    return absYA < absYB ? xA : xB;
                case LEFT_SIDE :
                    return xA;
                case RIGHT_SIDE :
                    return xB;
                case BELOW_SIDE :
                    return (yA <= 0) ? xA : xB;
                case ABOVE_SIDE :
                    return (yA <  0) ? xB : xA;
                default :
                    throw new MathInternalError(null);
                }
            }
            double targetY;
            if (agingA >= MAXIMAL_AGING) {
               targetY = REDUCTION_FACTOR * yB;
            } else if (agingB >= MAXIMAL_AGING) {
                targetY = REDUCTION_FACTOR * yA;
            } else {
                targetY = 0;
            }
            double nextX;
            int start = 0;
            int end   = nbPoints;
            do {
                System.arraycopy(x, start, tmpX, start, end - start);
                nextX = guessX(targetY, tmpX, y, start, end);
                if (!((nextX > xA) && (nextX < xB))) {
                    if (signChangeIndex - start >= end - signChangeIndex) {
                        ++start;
                    } else {
                        --end;
                    }
                    nextX = Double.NaN;
                }
            } while (Double.isNaN(nextX) && (end - start > 1));
            if (Double.isNaN(nextX)) {
                nextX = xA + 0.5 * (xB - xA);
                start = signChangeIndex - 1;
                end   = signChangeIndex;
            }
            final double nextY = computeObjectiveValue(nextX);
            if (Precision.equals(nextY, 0.0, 1)) {
                return nextX;
            }
            if ((nbPoints > 2) && (end - start != nbPoints)) {
                nbPoints = end - start;
                System.arraycopy(x, start, x, 0, nbPoints);
                System.arraycopy(y, start, y, 0, nbPoints);
                signChangeIndex -= start;
            } else  if (nbPoints == x.length) {
                nbPoints--;
                if (signChangeIndex >= (x.length + 1) / 2) {
                    System.arraycopy(x, 1, x, 0, nbPoints);
                    System.arraycopy(y, 1, y, 0, nbPoints);
                    --signChangeIndex;
                }
            }
            System.arraycopy(x, signChangeIndex, x, signChangeIndex + 1, nbPoints - signChangeIndex);
            x[signChangeIndex] = nextX;
            System.arraycopy(y, signChangeIndex, y, signChangeIndex + 1, nbPoints - signChangeIndex);
            y[signChangeIndex] = nextY;
            ++nbPoints;
            if (nextY * yA <= 0) {
                xB = nextX;
                yB = nextY;
                absYB = FastMath.abs(yB);
                ++agingA;
                agingB = 0;
            } else {
                xA = nextX;
                yA = nextY;
                absYA = FastMath.abs(yA);
                agingA = 0;
                ++agingB;
                signChangeIndex++;
            }
        }
    }
