    private void resolveLocalFileHeaderData(Map<ZipArchiveEntry, NameAndComment>
                                            entriesWithoutUTF8Flag)
        throws IOException {
           for (ZipArchiveEntry ze : entries.keySet()) {
                OffsetEntry offsetEntry = entries.get(ze);
                long offset = offsetEntry.headerOffset;
                archive.seek(offset + LFH_OFFSET_FOR_FILENAME_LENGTH);
                byte[] b = new byte[SHORT];
                archive.readFully(b);
                int fileNameLen = ZipShort.getValue(b);
                archive.readFully(b);
                int extraFieldLen = ZipShort.getValue(b);
                int lenToSkip = fileNameLen;
                while (lenToSkip > 0) {
                    int skipped = archive.skipBytes(lenToSkip);
                    if (skipped <= 0) {
                        throw new RuntimeException("failed to skip file name in"
                                                   + " local file header");
                    }
                    lenToSkip -= skipped;
                }
                byte[] localExtraData = new byte[extraFieldLen];
                archive.readFully(localExtraData);
                ze.setExtra(localExtraData
        }
    }
