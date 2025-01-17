    private final Object _deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        if (p.canReadTypeId()) {
            Object typeId = p.getTypeId();
            if (typeId != null) {
                return _deserializeWithNativeTypeId(p, ctxt, typeId);
            }
        }
       if (p.getCurrentToken() != JsonToken.START_OBJECT) {
            throw ctxt.wrongTokenException(p, JsonToken.START_OBJECT,
                    "need JSON Object to contain As.WRAPPER_OBJECT type information for class "+baseTypeName());
        }
        if (p.nextToken() != JsonToken.FIELD_NAME) {
            throw ctxt.wrongTokenException(p, JsonToken.FIELD_NAME,
                    "need JSON String that contains type id (for subtype of "+baseTypeName()+")");
        }
        final String typeId = p.getText();
        JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
        p.nextToken();
        if (_typeIdVisible && p.getCurrentToken() == JsonToken.START_OBJECT) {
            TokenBuffer tb = new TokenBuffer(null, false);
            tb.writeStartObject(); // recreate START_OBJECT
            tb.writeFieldName(_typePropertyName);
            tb.writeString(typeId);
            p = JsonParserSequence.createFlattened(tb.asParser(p), p);
            p.nextToken();
        }
        Object value = deser.deserialize(p, ctxt);
        if (p.nextToken() != JsonToken.END_OBJECT) {
            throw ctxt.wrongTokenException(p, JsonToken.END_OBJECT,
                    "expected closing END_OBJECT after type information and deserialized value");
        }
        return value;
    }
