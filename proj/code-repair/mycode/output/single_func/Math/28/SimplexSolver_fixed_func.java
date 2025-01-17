    private Integer getPivotRow(SimplexTableau tableau, final int col) {
        List<Integer> minRatioPositions = new ArrayList<Integer>();
        double minRatio = Double.MAX_VALUE;
        for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getHeight(); i++) {
            final double rhs = tableau.getEntry(i, tableau.getWidth() - 1);
            final double entry = tableau.getEntry(i, col);
            if (Precision.compareTo(entry, 0d, maxUlps) > 0) {
                final double ratio = rhs / entry;
                final int cmp = Double.compare(ratio, minRatio);
                if (cmp == 0) {
                    minRatioPositions.add(i);
                } else if (cmp < 0) {
                    minRatio = ratio;
                    minRatioPositions = new ArrayList<Integer>();
                    minRatioPositions.add(i);
                }
            }
        }
        if (minRatioPositions.size() == 0) {
            return null;
        } else if (minRatioPositions.size() > 1) {
           for (Integer row : minRatioPositions) {
                for (int i = 0; i < tableau.getNumArtificialVariables(); i++) {
                    int column = i + tableau.getArtificialVariableOffset();
                    final double entry = tableau.getEntry(row, column);
                    if (Precision.equals(entry, 1d, maxUlps) && row.equals(tableau.getBasicRow(column))) {
                        return row;
                    }
                }
            }
            Integer minRow = null;
            int minIndex = tableau.getWidth();
            for (Integer row : minRatioPositions) {
                int i = tableau.getNumObjectiveFunctions();
                for (; i < tableau.getWidth() - 1 && minRow != row; i++) {
                    if (row == tableau.getBasicRow(i)) {
                        if (i < minIndex) {
                            minIndex = i;
                            minRow = row;
                        }
                    }
                }
            }
            return
        }
        return minRatioPositions.get(0);
    }
