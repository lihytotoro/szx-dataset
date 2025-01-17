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
       } else if (offsetLocal > 0 && offsetAdjusted > 0) {
            long prev = previousTransition(instantAdjusted);
            if (prev < instantAdjusted) {
                int offsetPrev = getOffset(prev);
                int diff = offsetPrev - offsetLocal;
                if (instantAdjusted - prev <= diff) {
                    return offsetPrev;
                }
            }
        }
        return offsetAdjusted;
    }
