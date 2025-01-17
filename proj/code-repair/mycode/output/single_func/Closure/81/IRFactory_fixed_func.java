    Node processFunctionNode(FunctionNode functionNode) {
      Name name = functionNode.getFunctionName();
      Boolean isUnnamedFunction = false;
      if (name == null) {
       name = new Name();
        name.setLineno(functionNode.getLineno());
        name = new Name();
        name.setIdentifier("");
        isUnnamedFunction = true;
      }
      Node node = newNode(Token.FUNCTION);
      Node newName = transform(name);
      if (isUnnamedFunction) {
        newName.setLineno(functionNode.getLineno());
        int lpColumn = functionNode.getAbsolutePosition() +
            functionNode.getLp();
        newName.setCharno(position2charno(lpColumn));
      }
      node.addChildToBack(newName);
      Node lp = newNode(Token.LP);
      Name fnName = functionNode.getFunctionName();
      if (fnName != null) {
        lp.setLineno(fnName.getLineno());
      } else {
        lp.setLineno(functionNode.getLineno());
      }
      int lparenCharno = functionNode.getLp() +
          functionNode.getAbsolutePosition();
      lp.setCharno(position2charno(lparenCharno));
      for (AstNode param : functionNode.getParams()) {
        lp.addChildToBack(transform(param));
      }
      node.addChildToBack(lp);
      Node bodyNode = transform(functionNode.getBody());
      parseDirectives(bodyNode);
      node.addChildToBack(bodyNode);
     return node;
    }
