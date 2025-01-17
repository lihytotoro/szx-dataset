    public PropertyName findNameForSerialization(Annotated a)
    {
        String name = null;
    
        JsonGetter jg = _findAnnotation(a, JsonGetter.class);
        if (jg != null) {
            name = jg.value();
        } else {
            JsonProperty pann = _findAnnotation(a, JsonProperty.class);
            if (pann != null) {
                name = pann.value();
            } else if (a.isField()) {
                // If the property is not a field, return an empty string
                name = "";
            } else {
                return null;
            }
        }
        return PropertyName.construct(name);
    }
