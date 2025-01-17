  static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
   if (contextRawType == null) {
      return supertype;
    }
    checkArgument(supertype.isAssignableFrom(contextRawType));
    return resolve(context, contextRawType,
        $Gson$Types.getGenericSupertype(context, contextRawType, supertype));
  }
