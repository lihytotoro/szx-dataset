  private void visitCall(NodeTraversal t, Node n) {
    Node child = n.getFirstChild();
    JSType childType = getJSType(child).restrictByNotNullOrUndefined();
    if (!childType.canBeCalled()) {
      report(t, n, NOT_CALLABLE, childType.toString());
      ensureTyped(t, n);
      return;
    }
    if (childType instanceof FunctionType) {
      FunctionType functionType = (FunctionType) childType;
      boolean isExtern = false;
      JSDocInfo functionJSDocInfo = functionType.getJSDocInfo();
      if(functionJSDocInfo != null) {
        String sourceName = functionJSDocInfo.getSourceName();
        CompilerInput functionSource = compiler.getInput(sourceName);
        isExtern = functionSource.isExtern();
      }
      if (functionType.isConstructor() &&
          !functionType.isNativeObjectType() &&
          (functionType.getReturnType().isUnknownType() ||
           functionType.getReturnType().isVoidType() ||
           !isExtern)) {
        report(t, n, CONSTRUCTOR_NOT_CALLABLE, childType.toString());
      }
     else if (functionType.isInterface()) {
        report(t, n, CALL_INTERFACE, childType.toString());
      }
      visitParameterList(t, n, functionType);
      ensureTyped(t, n, functionType.getReturnType());
    } else {
      ensureTyped(t, n);
    }
  }
