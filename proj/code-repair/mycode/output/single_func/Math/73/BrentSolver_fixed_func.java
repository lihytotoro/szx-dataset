    public double solve(final UnivariateRealFunction f,
                        final double min, final double max, final double initial)
        throws MaxIterationsExceededException, FunctionEvaluationException {
        clearResult();
        verifySequence(min, initial, max);
        double yInitial = f.value(initial);
        if (Math.abs(yInitial) <= functionValueAccuracy) {
            setResult(initial, 0);
            return result;
        }
        double yMin = f.value(min);
        if (Math.abs(yMin) <= functionValueAccuracy) {
            setResult(yMin, 0);
            return result;
        }
        if (yInitial * yMin < 0) {
            return solve(f, min, yMin, initial, yInitial, min, yMin);
        }
        double yMax = f.value(max);
        if (Math.abs(yMax) <= functionValueAccuracy) {
            setResult(yMax, 0);
            return result;
        }
        if (yInitial * yMax < 0) {
            return solve(f, initial, yInitial, max, yMax, initial, yInitial);
        }
       if (yMin * yMax < 0) {
            return solve(f, min, yMin, max, yMax, min, yMin);
        }
        return solve(f, min, yMin, max, yMax, initial, yInitial);
    }
