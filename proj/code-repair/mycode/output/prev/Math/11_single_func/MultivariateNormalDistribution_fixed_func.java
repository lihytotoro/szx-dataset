    public double density(final double[] vals) throws DimensionMismatchException {
        final int dim = vals.length;
        if (dim != getDimension()) {
            throw new DimensionMismatchException(dim, getDimension());
        }
    
        return FastMath.pow(2 * FastMath.PI, -dim / 2) *
            FastMath.pow(covarianceMatrixDeterminant, -0.5) *
            getExponentTerm(vals);
    }
