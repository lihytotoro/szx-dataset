  if (NodeUtil.mayEffectMutableState(cArg, compiler)
      && (cArg.isParameterReference() && NodeUtil.mayEffectMutableState(
          NodeUtil.getFunction(cArg), compiler))) {
