    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof Complex)) {
            return false;
        }
    
        Complex rhs = (Complex)other;
        if (rhs.isNaN()) {
            return this.isNaN();
        } else {
            return (Double.doubleToRawLongBits(real) == Double.doubleToRawLongBits(rhs.getReal())) && (Double.doubleToRawLongBits(imaginary) == Double.doubleToRawLongBits(rhs.getImaginary()));
        }
    }
