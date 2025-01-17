    public int getOffsetFromLocal(long instantLocal) {
        final int offsetLocal = getOffset(instantLocal);
        final long instantAdjusted = instantLocal - offsetLocal;
        final int offsetAdjusted = getOffset(instantAdjusted);
        if (offsetLocal != offsetAdjusted) {
            if ((offsetLocal - offsetAdjusted) < 0) {
                long nextLocal = nextTransition(instantAdjusted);
                long nextAdjusted = nextTransition(instantLocal - offsetAdjusted);
                if (nextLocal != nextAdjusted) {
                    return offsetLocal;
                }
            }
           else {
                long previousLocal = previousTransition(instantAdjusted);
                long previousAdjusted = previousTransition(instantLocal - offsetAdjusted);
                if (previousLocal != previousAdjusted) {
                    return offsetLocal;
                }
            }
        }
        return offsetAdjusted;
    }
