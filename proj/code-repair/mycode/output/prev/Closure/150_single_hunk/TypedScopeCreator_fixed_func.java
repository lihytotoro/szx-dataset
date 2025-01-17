    @Override public void visit(NodeTraversal t, Node n, Node parent) {
      if (n == scope.getRootNode()) return;
    
      if (n.getType() == Token.LP && parent == scope.getRootNode()) {
        handleFunctionInputs(parent);
        return;
      }
    defineDeclaredFunction(n, parent);
    }
