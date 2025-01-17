    public char[] getTextBuffer()
    {
        // Are we just using shared input buffer?
        if (_inputStart >= 0) return _inputBuffer;
        if (_resultArray != null)  return _resultArray;
        if (_resultString != null) {
            _resultArray = new char[_resultString.length()];
            _resultString.getChars(0, _resultString.length(), _resultArray, 0);
            return _resultArray;
        }
        // Nope; but does it fit in just one segment?
        if (!_hasSegments)  return _currentSegment;
        // Nope, need to have/create a non-segmented array and return it
        return contentsAsArray();
    }
