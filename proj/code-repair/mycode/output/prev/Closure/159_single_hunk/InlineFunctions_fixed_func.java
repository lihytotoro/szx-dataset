  private void findCalledFunctions(
      Node node, Set<String> changed) {
    Preconditions.checkArgument(changed != null);
    // For each referenced function, add a new reference
  if (node.getType() == Token.CALL) {
      }
    }
  
    for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
      findCalledFunctions(c, changed);
    }
  }