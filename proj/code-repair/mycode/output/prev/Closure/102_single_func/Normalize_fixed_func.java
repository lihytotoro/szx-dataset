  NodeTraversal.traverse(compiler, root, this);
  if (MAKE_LOCAL_NAMES_UNIQUE) {
    MakeDeclaredNamesUnique renamer = new MakeDeclaredNamesUnique();
    NodeTraversal t = new NodeTraversal(compiler, renamer);
    t.traverseRoots(root, root);
  }
  removeDuplicateDeclarations(root);
  new PropogateConstantAnnotations(compiler, assertOnChange)
      .process(externs, root);
