    public JsonToken nextToken() throws IOException
    {
        _binaryValue = null;
        if (_nextToken != null) {
            JsonToken t = _nextToken;
            _currToken = t;
            _nextToken = null;
            switch (t) {
            case START_OBJECT:
                _parsingContext = _parsingContext.createChildObjectContext(-1, -1);
                break;
            case START_ARRAY:
                _parsingContext = _parsingContext.createChildArrayContext(-1, -1);
                break;
            case END_OBJECT:
            case END_ARRAY:
                _parsingContext = _parsingContext.getParent();
                _namesToWrap = _parsingContext.getNamesToWrap();
                break;
            case FIELD_NAME:
                _parsingContext.setCurrentName(_xmlTokens.getLocalName());
                break;
            default: // VALUE_STRING, VALUE_NULL
            }
            return t;
        }
        int token = _xmlTokens.next();
        while (token == XmlTokenStream.XML_START_ELEMENT) {
            if (_mayBeLeaf) {
                _nextToken = JsonToken.FIELD_NAME;
                _parsingContext = _parsingContext.createChildObjectContext(-1, -1);
                return (_currToken = JsonToken.START_OBJECT);
            }
            if (_parsingContext.inArray()) {
                token = _xmlTokens.next();
                _mayBeLeaf = true;
                continue;
            }
            String name = _xmlTokens.getLocalName();
            _parsingContext.setCurrentName(name);
            if (_namesToWrap != null && _namesToWrap.contains(name)) {
                _xmlTokens.repeatStartElement();
            }
            _mayBeLeaf = true;
            return (_currToken = JsonToken.FIELD_NAME);
        }
        switch (token) {
        case XmlTokenStream.XML_END_ELEMENT:
            if (_mayBeLeaf) {
                _mayBeLeaf = false;
                       if (_parsingContext.inArray()) {
                            if (_isEmpty(_currText)) {
                                _currToken = JsonToken.END_ARRAY;
                                _parsingContext = _parsingContext.getParent();
                                _namesToWrap = _parsingContext.getNamesToWrap();
                                return _currToken;
                            }
                        }
                    }
                }
                return (_currToken = JsonToken.VALUE_STRING);
            } else {
                if (_parsingContext.inObject()
                        && (_currToken != JsonToken.FIELD_NAME) && _isEmpty(_currText)) {
                    _currToken = JsonToken.END_OBJECT;
                    _parsingContext = _parsingContext.getParent();
                    _namesToWrap = _parsingContext.getNamesToWrap();
                    return _currToken;
                }
            }
            _parsingContext.setCurrentName(_cfgNameForTextElement);
            _nextToken = JsonToken.VALUE_STRING;
            return (_currToken = JsonToken.FIELD_NAME);
        case XmlTokenStream.XML_END:
            return (_currToken = null);
        }
        _throwInternal();
        return null;
    }
