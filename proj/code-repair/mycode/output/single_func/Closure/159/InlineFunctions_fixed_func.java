  private void findCalledFunctions(
      Node node, Set<String> changed) {
    Preconditions.checkArgument(changed != null);
     if (node.getType() == Token.CALL) {
        Node child = node.getFirstChild();
        if (child.getType() == Token.NAME) {
          changed.add(child.getString());
        }
      }
    }
    for (Node c = node.getFirstChild(); c != null; c = c.getNext()) {
      findCalledFunctions(c, changed);
    }
  }
