    public int readResults(byte[] b, int offset, int len) {
        if (base64.hasData()) {
            return base64.readResults(b, offset, len);
        } else {
            return -1;
        }
    }
