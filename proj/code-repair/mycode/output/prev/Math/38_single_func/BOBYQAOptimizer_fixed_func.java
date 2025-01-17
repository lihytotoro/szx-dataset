    private void prelim(double[] lowerBound,
                        double[] upperBound) {
        printMethod(); // XXX
    
        final int n = currentBest.getDimension();
        final int npt = numberOfInterpolationPoints;
        final int ndim = bMatrix.getRowDimension();
    
        final double rhosq = initialTrustRegionRadius * initialTrustRegionRadius;
        final double recip = 1d / rhosq;
        final int np = n + 1;
    
        // Set XBASE to the initial vector of variables, and set the initial
        // elements of XPT, BMAT, HQ, PQ and ZMAT to zero.
    
        for (int j = 0; j < n; j++) {
            originShift.setEntry(j, currentBest.getEntry(j));
            for (int k = 0; k < npt; k++) {
                interpolationPoints.setEntry(k, j, ZERO);
            }
            for (int i = 0; i < ndim; i++) {
                bMatrix.setEntry(i, j, ZERO);
            }
        }
        for (int i = 0, max = n * np / 2; i < max; i++) {
            modelSecondDerivativesValues.setEntry(i, ZERO);
        }
        for (int k = 0; k < npt; k++) {
            modelSecondDerivativesParameters.setEntry(k, ZERO);
            for (int j = 0, max = npt - np; j < max; j++) {
                zMatrix.setEntry(k, j, ZERO);
            }
        }
    
        // Begin the initialization procedure. NF becomes one more than the number
        // of function values so far. The coordinates of the displacement of the
        // next initial interpolation point from XBASE are set in XPT(NF+1,.).
    
        int ipt = 0;
        int jpt = 0;
        double fbeg = Double.NaN;
        do {
            final int nfm = getEvaluations();
            final int nfx = nfm - n;
            final int nfmm = nfm - 1;
            final int nfxm = nfx - 1;
            double stepa = 0;
            double stepb = 0;
            if (nfm <= 2 * n) {
                if (nfm >= 1 &&
                    nfm <= n) {
                    stepa = initialTrustRegionRadius;
                    if (upperDifference.getEntry(nfmm) == ZERO) {
                        stepa = -stepa;
                        throw new PathIsExploredException(); // XXX
                    }
                    interpolationPoints.setEntry(nfm, nfmm, stepa);
                } else if (nfm > n) {
                    stepa = interpolationPoints.getEntry(nfx, nfxm);
                    stepb = -initialTrustRegionRadius;
                    if (lowerDifference.getEntry(nfxm) == ZERO) {
                        stepb = Math.min(TWO * initialTrustRegionRadius, upperDifference.getEntry(nfxm));
                        throw new PathIsExploredException(); // XXX
                    }
                    if (upperDifference.getEntry(nfxm) == ZERO) {
                        stepb = Math.max(-TWO * initialTrustRegionRadius, lowerDifference.getEntry(nfxm));
                        throw new PathIsExploredException(); // XXX
                    }
                    interpolationPoints.setEntry(nfm, nfxm, stepb);
                }
            } else {
                final int tmp1 = (nfm - np) / n;
                jpt = nfm - tmp1 * n - n;
                ipt = jpt + tmp1;
                if (ipt > n) {
                    final int tmp2 = jpt;
                    jpt = ipt - n;
                    ipt = tmp2;
                    throw new PathIsExploredException(); // XXX
                }
                final int iptMinus1 = ipt;
                final int jptMinus1 = jpt;
                interpolationPoints.setEntry(nfm, iptMinus1, interpolationPoints.getEntry(ipt, iptMinus1));
                interpolationPoints.setEntry(nfm, jptMinus1, interpolationPoints.getEntry(jpt, jptMinus1));
            }
    
            // Calculate the next value of F. The least function value so far and
            // its index are required.
    
            for (int j = 0; j < n; j++) {
                currentBest.setEntry(j, Math.min(Math.max(lowerBound[j],
