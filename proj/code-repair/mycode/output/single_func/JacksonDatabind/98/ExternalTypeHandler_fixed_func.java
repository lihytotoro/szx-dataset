    public Object complete(JsonParser p, DeserializationContext ctxt,
            PropertyValueBuffer buffer, PropertyBasedCreator creator)
        throws IOException
    {
        final int len = _properties.length;
        Object[] values = new Object[len];
        for (int i = 0; i < len; ++i) {
            String typeId = _typeIds[i];
            final ExtTypedProperty extProp = _properties[i];
            if (typeId == null) {
                if (_tokens[i] == null) {
                    continue;
                }
                if (!extProp.hasDefaultType()) {
                    ctxt.reportInputMismatch(_beanType,
                            "Missing external type id property '%s'",
                            extProp.getTypePropertyName());
                } else {
                    typeId = extProp.getDefaultTypeId();
                }
            } else if (_tokens[i] == null) {
                SettableBeanProperty prop = extProp.getProperty();
                ctxt.reportInputMismatch(_beanType,
                        "Missing property '%s' for external type id '%s'",
                        prop.getName(), _properties[i].getTypePropertyName());
            }
            values[i] = _deserialize(p, ctxt, i, typeId);
            final SettableBeanProperty prop = extProp.getProperty();
            if (prop.getCreatorIndex() >= 0) {
                buffer.assignParameter(prop, values[i]);
                SettableBeanProperty typeProp = extProp.getTypeProperty();
                if ((typeProp != null) && (typeProp.getCreatorIndex() >= 0)) {
                   buffer.assignParameter(typeProp, values[i]);
                }
            }
        }
        Object bean = creator.build(ctxt, buffer);
        for (int i = 0; i < len; ++i) {
            SettableBeanProperty prop = _properties[i].getProperty();
            if (prop.getCreatorIndex() < 0) {
                prop.set(bean, values[i]);
            }
        }
        return bean;
    }
