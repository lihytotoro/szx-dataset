    public JsonSerializer<?> createContextual(SerializerProvider serializers,
            BeanProperty property) throws JsonMappingException
    {
        if (property == null) {
            return this;
        }
        JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
        if (format == null) {
            return this;
        }
        JsonFormat.Shape shape = format.getShape();
        if (shape.isNumeric()) {
            return withFormat(Boolean.TRUE, null);
        }
       if (shape == JsonFormat.Shape.STRING) {
            TimeZone tz = format.getTimeZone();
            final String pattern = format.hasPattern()
                            ? format.getPattern()
                            : StdDateFormat.DATE_FORMAT_STR_ISO8601;
            final Locale loc = format.hasLocale()
                            ? format.getLocale()
                            : serializers.getLocale();
            SimpleDateFormat df = new SimpleDateFormat(pattern, loc);
            if (tz == null) {
                tz = serializers.getTimeZone();
            }
            df.setTimeZone(tz);
            return withFormat(Boolean.FALSE, df);
        }
        return this;
    }
