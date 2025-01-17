    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException
    {
        if (_isInt) {
            visitIntFormat(visitor, typeHint, JsonParser.NumberType.BIG_INTEGER);
        } else {
            Class<?> h = handledType();
            if (h == BigDecimal.class) {
               visitFloatFormat(visitor, typeHint, JsonParser.NumberType.FLOAT);
            } else {
                /*JsonNumberFormatVisitor v2 =*/ visitor.expectNumberFormat(typeHint);
            }
        }
    }
