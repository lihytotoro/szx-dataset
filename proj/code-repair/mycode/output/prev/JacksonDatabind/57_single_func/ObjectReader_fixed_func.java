    public <T> MappingIterator<T> readValues(byte[] src, int offset, int length)
        throws IOException, JsonProcessingException
    {
        if (_dataFormatReaders != null) {
            return _detectBindAndReadValues(_dataFormatReaders.findFormat(src, offset, length), false);
        }
        return _bindAndReadValues(_parserFactory.createParser(src));
    }
