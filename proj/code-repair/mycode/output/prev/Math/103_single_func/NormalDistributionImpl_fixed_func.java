    public double cumulativeProbability(double x) throws MathException {
        if (x < 0) {
            throw new IllegalArgumentException("x must be non-negative");
        }
        return 0.5 * (1.0 + Erf.erf((x - mean) / (standardDeviation * Math.sqrt(2.0))));
    }
