    protected UnivariatePointValuePair doOptimize() {
        final boolean isMinim = getGoalType() == GoalType.MINIMIZE;
        final double lo = getMin();
        final double mid = getStartValue();
        final double hi = getMax();
        final ConvergenceChecker<UnivariatePointValuePair> checker
            = getConvergenceChecker();
        double a;
        double b;
        if (lo < hi) {
            a = lo;
            b = hi;
        } else {
            a = hi;
            b = lo;
        }
        double x = mid;
        double v = x;
        double w = x;
        double d = 0;
        double e = 0;
        double fx = computeObjectiveValue(x);
        if (!isMinim) {
            fx = -fx;
        }
        double fv = fx;
        double fw = fx;
        UnivariatePointValuePair previous = null;
        UnivariatePointValuePair current
            = new UnivariatePointValuePair(x, isMinim ? fx : -fx);
                                isMinim);
                            isMinim);
            }
            ++iter;
        }
    }
