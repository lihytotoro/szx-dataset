    public void close() throws IOException {
        if (!this.closed) {
            if (in != null) {
                in.close();
            }
            this.closed = true;
        }
    }
