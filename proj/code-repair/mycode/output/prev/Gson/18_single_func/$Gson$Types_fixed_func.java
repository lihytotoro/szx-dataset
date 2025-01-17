  static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
      // wildcards are useless for resolving supertypes. As the upper bound has the same raw type, use it instead
    checkArgument(supertype.isAssignableFrom(contextRawType));
    if (supertype.isAssignableFrom(contextRawType)) {
      return resolve(context, contextRawType,
          $Gson$Types.getGenericSupertype(context, contextRawType, supertype));
    } else {
      return resolve(context, contextRawType,
          $Gson$Types.getRawType(context, contextRawType, supertype));
    }
  }
