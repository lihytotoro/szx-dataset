    public T[] sample(int sampleSize) throws NotStrictlyPositiveException {
        if (sampleSize <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES,
                    sampleSize);
        }
    
        final T[] out = (T[]) java.lang.reflect.Array.newInstance(T.class, sampleSize);
    
        for (int i = 0; i < sampleSize; i++) {
            out[i] = sample();
        }
    
        return out;
    }
