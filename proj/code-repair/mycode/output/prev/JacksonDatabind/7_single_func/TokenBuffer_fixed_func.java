    public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException
    {
        copyCurrentStructure(jp);
        if (jp.currentToken() == JsonToken.FIELD_NAME) {
            jp.nextToken();
            if (jp.currentToken() != JsonToken.START_OBJECT) {
                throw new IOException("Expected token START_OBJECT, current token " + jp.currentToken());
            }
        }
        return this;
    }
