    public double[] fit() {
        final double[] guess = (new ParameterGuesser(getObservations())).guess();
        if (guess[0] < 0) {
            return fit(new Gaussian.Parametric(), new double[] {0, 0});
        }
        return fit(new Gaussian.Parametric(), guess);
    }
