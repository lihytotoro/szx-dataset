  void tryMinimizeExits(Node n, int exitType, String labelName) {
    if (matchingExitNode(n, exitType, labelName)) {
      NodeUtil.removeChild(n.getParent(), n);
      compiler.reportCodeChange();
      return;
    }
    if (n.isIf()) {
      Node ifBlock = n.getFirstChild().getNext();
      tryMinimizeExits(ifBlock, exitType, labelName);
      Node elseBlock = ifBlock.getNext();
      if (elseBlock != null) {
        tryMinimizeExits(elseBlock, exitType, labelName);
      }
      return;
    }
    if (n.isTry()) {
      Node tryBlock = n.getFirstChild();
      tryMinimizeExits(tryBlock, exitType, labelName);
      Node allCatchNodes = NodeUtil.getCatchBlock(n);
      if (NodeUtil.hasCatchHandler(allCatchNodes)) {
        Preconditions.checkState(allCatchNodes.hasOneChild());
        Node catchNode = allCatchNodes.getFirstChild();
        Node catchCodeBlock = catchNode.getLastChild();
        tryMinimizeExits(catchCodeBlock, exitType, labelName);
      }
      /* Don't try to minimize the exits of finally blocks, as this
       * can cause problems if it changes the completion type of the finally
       * block. See ECMA 262 Sections 8.9 & 12.14
       */
     if (NodeUtil.hasFinally(n)) {
        Node finallyBlock = n.getLastChild();
        tryMinimizeExits(finallyBlock, exitType, labelName);
      }
    }
    if (n.isLabel()) {
      Node labelBlock = n.getLastChild();
      tryMinimizeExits(labelBlock, exitType, labelName);
    }
    if (!n.isBlock() || n.getLastChild() == null) {
      return;
    }
    for (Node c : n.children()) {
      if (c.isIf()) {
        Node ifTree = c;
        Node trueBlock, falseBlock;
        trueBlock = ifTree.getFirstChild().getNext();
        falseBlock = trueBlock.getNext();
        tryMinimizeIfBlockExits(trueBlock, falseBlock,
            ifTree, exitType, labelName);
        trueBlock = ifTree.getFirstChild().getNext();
        falseBlock = trueBlock.getNext();
        if (falseBlock != null) {
          tryMinimizeIfBlockExits(falseBlock, trueBlock,
              ifTree, exitType, labelName);
        }
      }
      if (c == n.getLastChild()) {
        break;
      }
    }
    for (Node c = n.getLastChild(); c != null; c = n.getLastChild()) {
      tryMinimizeExits(c, exitType, labelName);
      if (c == n.getLastChild()) {
        break;
      }
    }
  }
