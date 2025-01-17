    public void validateSubType(DeserializationContext ctxt, JavaType type) throws JsonMappingException
    {
        final Class<?> raw = type.getRawClass();
        String full = raw.getName();
        main_check:
        do {
            if (_cfgIllegalClassNames.contains(full)) {
                break;
            }
           for (Class<?> cls = raw; cls != Object.class; cls = cls.getSuperclass()) {
                if (full.startsWith(PREFIX_STRING)) {
                    String name = cls.getSimpleName();
                    if ("AbstractPointcutAdvisor".equals(name)
                            || "AbstractApplicationContext".equals(name)) {
                        break main_check;
                    }
                }
            }
            return;
        } while (false);
        throw JsonMappingException.from(ctxt,
                String.format("Illegal type (%s) to deserialize: prevented for security reasons", full));
    }
