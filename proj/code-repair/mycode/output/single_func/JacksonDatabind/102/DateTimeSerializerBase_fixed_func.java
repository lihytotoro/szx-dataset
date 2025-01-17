    public JsonSerializer<?> createContextual(SerializerProvider serializers,
            BeanProperty property) throws JsonMappingException
    {
       if (property == null || handledType() == null) {
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
        if (format.hasPattern()) {
            final Locale loc = format.hasLocale()
                            ? format.getLocale()
                            : serializers.getLocale();
            SimpleDateFormat df = new SimpleDateFormat(format.getPattern(), loc);
            TimeZone tz = format.hasTimeZone() ? format.getTimeZone()
                    : serializers.getTimeZone();
            df.setTimeZone(tz);
            return withFormat(Boolean.FALSE, df);
        }
        final boolean hasLocale = format.hasLocale();
        final boolean hasTZ = format.hasTimeZone();
        final boolean asString = (shape == JsonFormat.Shape.STRING);
        if (!hasLocale && !hasTZ && !asString) {
            return this;
        }
        DateFormat df0 = serializers.getConfig().getDateFormat();
        if (df0 instanceof StdDateFormat) {
            StdDateFormat std = (StdDateFormat) df0;
            if (format.hasLocale()) {
                std = std.withLocale(format.getLocale());
            }
            if (format.hasTimeZone()) {
                std = std.withTimeZone(format.getTimeZone());
            }
            return withFormat(Boolean.FALSE, std);
        }
        if (!(df0 instanceof SimpleDateFormat)) {
            serializers.reportBadDefinition(handledType(), String.format(
    figured `DateFormat` (%s) not a `SimpleDateFormat`; cannot configure `Locale` or `TimeZone`",
    getClass().getName()));
        }
        SimpleDateFormat df = (SimpleDateFormat) df0;
        if (hasLocale) {
            df = new SimpleDateFormat(df.toPattern(), format.getLocale());
        } else {
            df = (SimpleDateFormat) df.clone();
        }
        TimeZone newTz = format.getTimeZone();
        boolean changeTZ = (newTz != null) && !newTz.equals(df.getTimeZone());
        if (changeTZ) {
            df.setTimeZone(newTz);
        }
        return withFormat(Boolean.FALSE, df);
    }
