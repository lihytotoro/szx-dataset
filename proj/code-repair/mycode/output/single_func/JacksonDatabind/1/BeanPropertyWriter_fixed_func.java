    public void serializeAsColumn(Object bean, JsonGenerator jgen, SerializerProvider prov)
        throws Exception
    {
        Object value = get(bean);
        if (value == null) { // nulls need specialized handling
            if (_nullSerializer != null) {
                _nullSerializer.serialize(null, jgen, prov);
            } else { // can NOT suppress entries in tabular output
                jgen.writeNull();
            }
           return;
        }
        JsonSerializer<Object> ser = _serializer;
        if (ser == null) {
            Class<?> cls = value.getClass();
            PropertySerializerMap map = _dynamicSerializers;
            ser = map.serializerFor(cls);
            if (ser == null) {
                ser = _findAndAddDynamic(map, cls, prov);
            }
        }
        if (_suppressableValue != null) {
            if (MARKER_FOR_EMPTY == _suppressableValue) {
                if (ser.isEmpty(value)) { // can NOT suppress entries in tabular output
                    serializeAsPlaceholder(bean, jgen, prov);
                    return;
                }
            } else if (_suppressableValue.equals(value)) { // can NOT suppress entries in tabular output
                serializeAsPlaceholder(bean, jgen, prov);
                return;
            }
        }
        if (value == bean) {
            _handleSelfReference(bean, ser);
        }
        if (_typeSerializer == null) {
            ser.serialize(value, jgen, prov);
        } else {
            ser.serializeWithType(value, jgen, prov, _typeSerializer);
        }
    }
