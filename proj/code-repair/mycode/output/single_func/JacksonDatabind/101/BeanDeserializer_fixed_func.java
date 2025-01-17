    protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser p, DeserializationContext ctxt)
        throws IOException
    {
        final PropertyBasedCreator creator = _propertyBasedCreator;
        PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, _objectIdReader);
        TokenBuffer tokens = new TokenBuffer(p, ctxt);
        tokens.writeStartObject();
        JsonToken t = p.getCurrentToken();
        for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
            String propName = p.getCurrentName();
            p.nextToken(); // to point to value
            SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
            if (creatorProp != null) {
                if (buffer.assignParameter(creatorProp,
                        _deserializeWithErrorWrapping(p, ctxt, creatorProp))) {
                    t = p.nextToken(); // to move to following FIELD_NAME/END_OBJECT
                    Object bean;
                    try {
                        bean = creator.build(ctxt, buffer);
                    } catch (Exception e) {
                        bean = wrapInstantiationProblem(e, ctxt);
                    }
                    p.setCurrentValue(bean);
                    while (t == JsonToken.FIELD_NAME) {
                       tokens.copyCurrentStructure(p);
                        t = p.nextToken();
                    }
                    tokens.writeEndObject();
                    if (bean.getClass() != _beanType.getRawClass()) {
                        ctxt.reportInputMismatch(creatorProp,
                                "Cannot create polymorphic instances with unwrapped values");
                        return null;
                    }
                    return _unwrappedPropertyHandler.processUnwrapped(p, ctxt, bean, tokens);
                }
                continue;
            }
            if (buffer.readIdProperty(propName)) {
                continue;
            }
            SettableBeanProperty prop = _beanProperties.find(propName);
            if (prop != null) {
                buffer.bufferProperty(prop, _deserializeWithErrorWrapping(p, ctxt, prop));
                continue;
            }
            if (_ignorableProps != null && _ignorableProps.contains(propName)) {
                handleIgnoredProperty(p, ctxt, handledType(), propName);
                continue;
            }
            if (_anySetter == null) {
                tokens.writeFieldName(propName);
                tokens.copyCurrentStructure(p);
            } else {
                TokenBuffer b2 = TokenBuffer.asCopyOfValue(p);
                tokens.writeFieldName(propName);
                tokens.append(b2);
                try {
                    buffer.bufferAnyProperty(_anySetter, propName,
                            _anySetter.deserialize(b2.asParserOnFirstToken(), ctxt));
                } catch (Exception e) {
                    wrapAndThrow(e, _beanType.getRawClass(), propName, ctxt);
                }
                continue;
            }
        }
        Object bean;
        try {
            bean = creator.build(ctxt, buffer);
        } catch (Exception e) {
            wrapInstantiationProblem(e, ctxt);
            return null; // never gets here
        }
        return _unwrappedPropertyHandler.processUnwrapped(p, ctxt, bean, tokens);
    }
