  private Node tryFoldArrayJoin(Node n) {
    Node callTarget = n.getFirstChild();
    if (callTarget == null || !NodeUtil.isGetProp(callTarget)) {
      return n;
    }
    Node right = callTarget.getNext();
    if (right != null) {
     Node arrayNode = callTarget.getFirstChild();
      Node functionName = arrayNode.getNext();
      if ((arrayNode.getType() != Token.ARRAYLIT) ||
          !functionName.getString().equals("join")) {
        return n;
      }
      if (!NodeUtil.isImmutableValue(right)) {
        return n;
      }
    }
    String joinString = (right == null) ? "," : NodeUtil.getStringValue(right);
    List<Node> arrayFoldedChildren = Lists.newLinkedList();
    StringBuilder sb = null;
    int foldedSize = 0;
    Node prev = null;
    Node elem = arrayNode.getFirstChild();
    while (elem != null) {
      if (NodeUtil.isImmutableValue(elem) || elem.getType() == Token.EMPTY) {
        if (sb == null) {
          sb = new StringBuilder();
        } else {
          sb.append(joinString);
        }
        sb.append(NodeUtil.getArrayElementStringValue(elem));
      } else {
        if (sb != null) {
          Preconditions.checkNotNull(prev);
          foldedSize += sb.length() + 2;
          arrayFoldedChildren.add(
              Node.newString(sb.toString()).copyInformationFrom(prev));
          sb = null;
        }
        foldedSize += InlineCostEstimator.getCost(elem);
        arrayFoldedChildren.add(elem);
      }
      prev = elem;
      elem = elem.getNext();
    }
    if (sb != null) {
      Preconditions.checkNotNull(prev);
      foldedSize += sb.length() + 2;
      arrayFoldedChildren.add(
          Node.newString(sb.toString()).copyInformationFrom(prev));
    }
    foldedSize += arrayFoldedChildren.size() - 1;
    int originalSize = InlineCostEstimator.getCost(n);
    switch (arrayFoldedChildren.size()) {
      case 0:
        Node emptyStringNode = Node.newString("");
        n.getParent().replaceChild(n, emptyStringNode);
        reportCodeChange();
        return emptyStringNode;
      case 1:
        Node foldedStringNode = arrayFoldedChildren.remove(0);
        if (foldedSize > originalSize) {
          return n;
        }
        arrayNode.detachChildren();
        if (foldedStringNode.getType() != Token.STRING) {
          Node replacement = new Node(Token.ADD,
              Node.newString("").copyInformationFrom(n),
              foldedStringNode);
          foldedStringNode = replacement;
        }
        n.getParent().replaceChild(n, foldedStringNode);
        reportCodeChange();
        return foldedStringNode;
      default:
        if (arrayFoldedChildren.size() == arrayNode.getChildCount()) {
          return n;
        }
        int kJoinOverhead = "[].join()".length();
        foldedSize += kJoinOverhead;
        foldedSize += (right != null) ? InlineCostEstimator.getCost(right) : 0;
        if (foldedSize > originalSize) {
          return n;
        }
        arrayNode.detachChildren();
        for (Node node : arrayFoldedChildren) {
          arrayNode.addChildToBack(node);
        }
        reportCodeChange();
        break;
    }
    return n;
  }
