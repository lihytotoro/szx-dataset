    protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser p, DeserializationContext ctxt)
        throws IOException
    {
        final ExternalTypeHandler ext = _externalTypeIdHandler.start();
        final PropertyBasedCreator creator = _propertyBasedCreator;
        PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, _objectIdReader);
        TokenBuffer tokens = new TokenBuffer(p);
        tokens.writeStartObject();
        JsonToken t = p.getCurrentToken();
        for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
            String propName = p.getCurrentName();
            p.nextToken(); // to point to value
            SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
            if (creatorProp != null) {
               if (ext.handlePropertyValue(p, ctxt, propName, null)) {
                    ;
                } else {
                    if (buffer.assignParameter(creatorProp, _deserializeWithErrorWrapping(p, ctxt, creatorProp))) {
                        t = p.nextToken(); // to move to following FIELD_NAME/END_OBJECT
                        Object bean;
                        try {
                            bean = creator.build(ctxt, buffer);
                        } catch (Exception e) {
                            wrapAndThrow(e, _beanType.getRawClass(), propName, ctxt);
                            continue; // never gets here
                        }
                        while (t == JsonToken.FIELD_NAME) {
                            p.nextToken(); // to skip name
                            tokens.copyCurrentStructure(p);
                            t = p.nextToken();
                        }
                        if (bean.getClass() != _beanType.getRawClass()) {
                            throw ctxt.mappingException("Can not create polymorphic instances with unwrapped values");
                        }
                        return ext.complete(p, ctxt, bean);
                    }
                }
                continue;
            }
            if (buffer.readIdProperty(propName)) {
                continue;
            }
            SettableBeanProperty prop = _beanProperties.find(propName);
            if (prop != null) {
                buffer.bufferProperty(prop, prop.deserialize(p, ctxt));
                continue;
            }
            if (ext.handlePropertyValue(p, ctxt, propName, null)) {
                continue;
            }
            /* As per [JACKSON-313], things marked as ignorable should not be
             * passed to any setter
             */
            if (_ignorableProps != null && _ignorableProps.contains(propName)) {
                handleIgnoredProperty(p, ctxt, handledType(), propName);
                continue;
            }
            if (_anySetter != null) {
                buffer.bufferAnyProperty(_anySetter, propName, _anySetter.deserialize(p, ctxt));
            }
        }
        try {
            return ext.complete(p, ctxt, buffer, creator);
        } catch (Exception e) {
            wrapInstantiationProblem(e, ctxt);
            return null; // never gets here
        }
    }
