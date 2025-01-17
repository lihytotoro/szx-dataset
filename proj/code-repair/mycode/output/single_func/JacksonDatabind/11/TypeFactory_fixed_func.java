    protected JavaType _fromVariable(TypeVariable<?> type, TypeBindings context)
    {
        final String name = type.getName();
        if (context == null) {
           JavaType actualType = context.findType(name);
            if (actualType == null) {
                return _unknownType();
            }
        } else {
            JavaType actualType = context.findType(name);
            if (actualType != null) {
                return actualType;
            }
        }
        /* 29-Jan-2010, tatu: We used to throw exception here, if type was
         *   bound: but the problem is that this can occur for generic "base"
         *   method, overridden by sub-class. If so, we will want to ignore
         *   current type (for method) since it will be masked.
         */
        Type[] bounds = type.getBounds();
        /* 29-Jan-2010, tatu: One more problem are recursive types
         *   (T extends Comparable<T>). Need to add "placeholder"
         *   for resolution to catch those.
         */
        context._addPlaceholder(name);
        return _constructType(bounds[0], context);
    }
