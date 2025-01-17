    public Class getGenericType(Field field) {        
        Type generic = field.getGenericType();
        if (generic != null && generic instanceof ParameterizedType) {
            Type actual = ((ParameterizedType) generic).getActualTypeArguments()[0];
           if (actual instanceof Class) {
                return (Class) actual;
            } else {
                return Object.class;
            }
        }
        return Object.class;
    }
