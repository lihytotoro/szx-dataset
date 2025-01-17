    public TarArchiveEntry getNextTarEntry() throws IOException {
        if (hasHitEOF) {
            return null;
        }
        if (currEntry != null) {
            long numToSkip = entrySize - entryOffset;
            while (numToSkip > 0) {
                long skipped = skip(numToSkip);
                if (skipped <= 0) {
                    throw new RuntimeException("failed to skip current tar entry");
                }
                numToSkip -= skipped;
            }
            readBuf = null;
        }
        byte[] headerBuf = getRecord();
        if (hasHitEOF) {
            currEntry = null;
            return null;
        }
       currEntry = TarArchiveEntry.createEntry(headerBuf);
        entryOffset = 0;
        entrySize = currEntry.getSize();
        if (currEntry.isGNULongNameEntry()) {
            StringBuffer longName = new StringBuffer();
            byte[] buf = new byte[SMALL_BUFFER_SIZE];
            int length = 0;
            while ((length = read(buf)) >= 0) {
                longName.append(new String(buf, 0, length));
            }
            getNextEntry();
            if (currEntry == null) {
                return null;
            }
            if (longName.length() > 0
                && longName.charAt(longName.length() - 1) == 0) {
                longName.deleteCharAt(longName.length() - 1);
            }
            currEntry.setName(longName.toString());
        }
        if (currEntry.isPaxHeader()){ // Process Pax headers
            paxHeaders();
        }
        if (currEntry.isGNUSparse()){ // Process sparse files
            readGNUSparse();
        }
        entrySize = currEntry.getSize();
        return currEntry;
    }
