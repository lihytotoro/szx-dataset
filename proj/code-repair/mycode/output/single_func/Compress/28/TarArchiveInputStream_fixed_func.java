    public int read(byte[] buf, int offset, int numToRead) throws IOException {
    	int totalRead = 0;
        if (hasHitEOF || entryOffset >= entrySize) {
            return -1;
        }
        if (currEntry == null) {
            throw new IllegalStateException("No current tar entry");
        }
        numToRead = Math.min(numToRead, available());
        totalRead = is.read(buf, offset, numToRead);
       if (totalRead != -1) {
            count(totalRead);
            if (totalRead == -1) {
                hasHitEOF = true;
            }
            entryOffset += totalRead;
        }
        return totalRead;
    }
