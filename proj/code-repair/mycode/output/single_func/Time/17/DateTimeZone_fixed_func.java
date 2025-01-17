    public long adjustOffset(long instant, boolean earlierOrLater) {
       long instantBefore = convertUTCToLocal(instant - 2 * DateTimeConstants.MILLIS_PER_HOUR);
        long instantAfter = convertUTCToLocal(instant + 2 * DateTimeConstants.MILLIS_PER_HOUR);
        if (instantBefore == instantAfter) {
            return instant;  // not an overlap (less than is a gap, equal is normal case)
        }
        long local = convertUTCToLocal(instant);
        return convertLocalToUTC(local, false, earlierOrLater ? instantAfter : instantBefore);
    }
