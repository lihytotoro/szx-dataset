    protected JavaType _typeFromId(String id, DatabindContext ctxt) throws IOException
    {
        TypeFactory tf = ctxt.getTypeFactory();
        if (id.indexOf('<') > 0) {
            JavaType t = tf.constructFromCanonical(id);
            return t;
        }
        Class<?> cls;
        try {
            cls =  tf.findClass(id);
        } catch (ClassNotFoundException e) {
            if (ctxt instanceof DeserializationContext) {
                DeserializationContext dctxt = (DeserializationContext) ctxt;
                return dctxt.handleUnknownTypeId(_baseType, id, this, "no such class found");
            }
            throw new IllegalArgumentException("Invalid type id '"+id+"' (for id type 'Id.class'): "+e.getMessage(), e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid type id '"+id+"' (for id type 'Id.class'): "+e.getMessage(), e);
        }
        return tf.constructSpecializedType(_baseType, cls);
    }
