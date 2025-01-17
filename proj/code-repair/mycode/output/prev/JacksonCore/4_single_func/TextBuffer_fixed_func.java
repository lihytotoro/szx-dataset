    public char[] expandCurrentSegment() {
        final char[] curr = _currentSegment;
        // Let's grow by 50% by default
        final int len = curr.length;
        // but above intended maximum, slow to increase by 25%
        int newLen = (len == MAX_SEGMENT_LEN) ? (MAX_SEGMENT_LEN+1) : Math.min(MAX_SEGMENT_LEN, len * 1.5);
        return (_currentSegment = Arrays.copyOf(curr, newLen));
    }
