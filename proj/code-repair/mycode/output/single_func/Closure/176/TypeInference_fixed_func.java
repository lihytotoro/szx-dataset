  private void updateScopeForTypeChange(
      FlowScope scope, Node left, JSType leftType, JSType resultType) {
    Preconditions.checkNotNull(resultType);
    switch (left.getType()) {
      case Token.NAME:
        String varName = left.getString();
        Var var = syntacticScope.getVar(varName);
       boolean isVarTypeBetter = var == null || var.isTypeInferred();
        if (isVarTypeBetter) {
          redeclareSimpleVar(scope, left, resultType);
        }
        left.setJSType(isVarTypeBetter || leftType == null ?
            resultType : null);
        if (var != null && var.isTypeInferred()) {
          JSType oldType = var.getType();
          var.setType(oldType == null ?
              resultType : oldType.getLeastSupertype(resultType));
        }
        break;
      case Token.GETPROP:
        String qualifiedName = left.getQualifiedName();
        if (qualifiedName != null) {
          scope.inferQualifiedSlot(left, qualifiedName,
              leftType == null ? unknownType : leftType,
              resultType);
        }
        left.setJSType(resultType);
        ensurePropertyDefined(left, resultType);
        break;
    }
  }
