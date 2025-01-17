    public void visit(NodeTraversal t, Node n, Node parent) {
      if (t.inGlobalScope()) {
        if (NodeUtil.isVarDeclaration(n)) {
          NameInformation ns = createNameInformation(t, n, parent);
          Preconditions.checkNotNull(ns);
          recordSet(ns.name, n);
        } else if (NodeUtil.isFunctionDeclaration(n)) {
          Node nameNode = n.getFirstChild();
          NameInformation ns = createNameInformation(t, nameNode, n);
          if (ns != null) {
            JsName nameInfo = getName(nameNode.getString(), true);
            recordSet(nameInfo.name, nameNode);
          }
        } else if (NodeUtil.isObjectLitKey(n, parent)) {
          NameInformation ns = createNameInformation(t, n, parent);
          if (ns != null) {
            recordSet(ns.name, n);
          }
        }
      }
      if (n.isAssign()) {
        Node nameNode = n.getFirstChild();
        NameInformation ns = createNameInformation(t, nameNode, n);
        if (ns != null) {
          if (ns.isPrototype) {
            recordPrototypeSet(ns.prototypeClass, ns.prototypeProperty, n);
          } else {
            recordSet(ns.name, nameNode);
          }
        }
      } else if (n.isCall()) {
        Node nameNode = n.getFirstChild();
        NameInformation ns = createNameInformation(t, nameNode, n);
        if (ns != null && ns.onlyAffectsClassDef) {
           refNodes.add(new ClassDefiningFunctionNode(
                getName(ns.name, false), n, parent, parent.getParent()));
        }
      }
    }
