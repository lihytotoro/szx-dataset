  private boolean hasExceptionHandler(Node cfgNode) {
    for (Node child : cfgNode.getChildren()) {
      if (child.getType() == NodeType.EXCEPTION_HANDLER) {
        return true;
      }
    }
    return false;
  }
