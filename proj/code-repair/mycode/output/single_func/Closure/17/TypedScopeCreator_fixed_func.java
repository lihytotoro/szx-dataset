    private JSType getDeclaredType(String sourceName, JSDocInfo info,
        Node lValue, @Nullable Node rValue) {
      if (info != null && info.hasType()) {
        return getDeclaredTypeInAnnotation(sourceName, lValue, info);
      } else if (rValue != null && rValue.isFunction() &&
          shouldUseFunctionLiteralType(
              JSType.toMaybeFunctionType(rValue.getJSType()), info, lValue)) {
        return rValue.getJSType();
      } else if (info != null) {
        if (info.hasEnumParameterType()) {
          if (rValue != null && rValue.isObjectLit()) {
            return rValue.getJSType();
          } else {
            return createEnumTypeFromNodes(
                rValue, lValue.getQualifiedName(), info, lValue);
          }
        } else if (info.isConstructor() || info.isInterface()) {
          return createFunctionTypeFromNodes(
              rValue, lValue.getQualifiedName(), info, lValue);
        } else {
          if (info.isConstant()) {
            JSType knownType = null;
            if (rValue != null) {
             if (rValue.getJSType() != null && !rValue.getJSType().isUnknownType()
                  && !rValue.getJSType().isAllType()) {
                return rValue.getJSType();
              } else if (rValue.isOr()) {
                Node firstClause = rValue.getFirstChild();
                Node secondClause = firstClause.getNext();
                boolean namesMatch = firstClause.isName()
                    && lValue.isName()
                    && firstClause.getString().equals(lValue.getString());
                if (namesMatch && secondClause.getJSType() != null
                    && !secondClause.getJSType().isUnknownType()) {
                  return secondClause.getJSType();
                }
              }
            }
          }
        }
      }
      return getDeclaredTypeInAnnotation(sourceName, lValue, info);
    }
