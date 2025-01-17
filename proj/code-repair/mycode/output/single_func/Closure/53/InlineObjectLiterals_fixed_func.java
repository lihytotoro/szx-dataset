    private void replaceAssignmentExpression(Var v, Reference ref,
                                             Map<String, String> varmap) {
      List<Node> nodes = Lists.newArrayList();
      Node val = ref.getAssignedValue();
      blacklistVarReferencesInTree(val, v.scope);
      Preconditions.checkState(val.getType() == Token.OBJECTLIT);
      Set<String> all = Sets.newLinkedHashSet(varmap.keySet());
      for (Node key = val.getFirstChild(); key != null;
           key = key.getNext()) {
        String var = key.getString();
        Node value = key.removeFirstChild();
        nodes.add(
          new Node(Token.ASSIGN,
                   Node.newString(Token.NAME, varmap.get(var)), value));
        all.remove(var);
      }
      for (String var : all) {
        nodes.add(
          new Node(Token.ASSIGN,
                   Node.newString(Token.NAME, varmap.get(var)),
                   NodeUtil.newUndefinedNode(null)));
      }
      Node replacement;
     nodes.add(Node.newBoolean(true));
      nodes = Lists.reverse(nodes);
      replacement = new Node(Token.COMMA);
      Node cur = replacement;
      int i;
      for (i = 0; i < nodes.size() - 2; i++) {
        cur.addChildToFront(nodes.get(i));
        Node t = new Node(Token.COMMA);
        cur.addChildToFront(t);
        cur = t;
      }
      cur.addChildToFront(nodes.get(i));
      cur.addChildToFront(nodes.get(i + 1));
      Node replace = ref.getParent();
      replacement.copyInformationFromForTree(replace);
      if (replace.getType() == Token.VAR) {
        replace.getParent().replaceChild(
            replace, NodeUtil.newExpr(replacement));
      } else {
        replace.getParent().replaceChild(replace, replacement);
      }
    }
