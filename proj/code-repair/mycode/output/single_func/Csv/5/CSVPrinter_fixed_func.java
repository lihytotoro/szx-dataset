    public void println() throws IOException {
        final String recordSeparator = format.getRecordSeparator();
       out.append(recordSeparator);
        newRecord = true;
    }
