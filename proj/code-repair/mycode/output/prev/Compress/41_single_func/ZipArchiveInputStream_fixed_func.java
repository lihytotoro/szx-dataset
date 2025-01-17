    private void processZip64Extra(ZipLong size, ZipLong cSize) {
        if (size != null && size.getValue() == ZipConstants.ZIP64_MAGIC) {
            current.entry.setCompressedSize(ZipConstants.ZIP64_MAGIC);
        }
        if (cSize != null && cSize.getValue() == ZipConstants.ZIP64_MAGIC) {
            current.entry.setUncompressedSize(ZipConstants.ZIP64_MAGIC);
        }
    }
