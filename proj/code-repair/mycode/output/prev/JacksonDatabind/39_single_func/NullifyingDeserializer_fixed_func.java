    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        // 29-Jan-2016, tatu: Simple skipping for all other tokens, but FIELD_NAME bit
        //    special unfortunately
        if (p.getCurrentToken() == JsonToken.FIELD_NAME) {
            p.skipChildren();
        }
        return null;
    }
