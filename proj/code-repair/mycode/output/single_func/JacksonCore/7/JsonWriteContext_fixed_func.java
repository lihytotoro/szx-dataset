    public int writeValue() {
        if (_type == TYPE_OBJECT) {
           if (_gotName) {
                ++_index;
            }
            _gotName = false;
            ++_index;
            return STATUS_OK_AFTER_COLON;
        }
        if (_type == TYPE_ARRAY) {
            int ix = _index;
            ++_index;
            return (ix < 0) ? STATUS_OK_AS_IS : STATUS_OK_AFTER_COMMA;
        }
        ++_index;
        return (_index == 0) ? STATUS_OK_AS_IS : STATUS_OK_AFTER_SPACE;
    }
