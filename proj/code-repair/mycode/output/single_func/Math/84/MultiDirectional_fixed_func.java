    protected void iterateSimplex(final Comparator<RealPointValuePair> comparator)
        throws FunctionEvaluationException, OptimizationException, IllegalArgumentException {
           while (true) {
                incrementIterationsCounter();
                final RealPointValuePair[] original = simplex;
                final RealPointValuePair best = original[0];
                final RealPointValuePair reflected = evaluateNewSimplex(original, 1.0, comparator);
                if (comparator.compare(reflected, best) < 0) {
                    final RealPointValuePair[] reflectedSimplex = simplex;
                    final RealPointValuePair expanded = evaluateNewSimplex(original, khi, comparator);
                    if (comparator.compare(reflected, expanded) <= 0) {
                        simplex = reflectedSimplex;
                    }
                    return;
                }
                final RealPointValuePair contracted = evaluateNewSimplex(original, gamma, comparator);
                if (comparator.compare(contracted, best) < 0) {
                    continue;
                }
                return;
            }
        }
    }
