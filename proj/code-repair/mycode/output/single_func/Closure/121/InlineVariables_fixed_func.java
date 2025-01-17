    private void inlineNonConstants(
        Var v, ReferenceCollection referenceInfo,
        boolean maybeModifiedArguments) {
      int refCount = referenceInfo.references.size();
      Reference declaration = referenceInfo.references.get(0);
      Reference init = referenceInfo.getInitializingReference();
      int firstRefAfterInit = (declaration == init) ? 2 : 3;
      if (refCount > 1 &&
          isImmutableAndWellDefinedVariable(v, referenceInfo)) {
        Node value;
        if (init != null) {
          value = init.getAssignedValue();
        } else {
          Node srcLocation = declaration.getNode();
          value = NodeUtil.newUndefinedNode(srcLocation);
        }
        Preconditions.checkNotNull(value);
        inlineWellDefinedVariable(v, value, referenceInfo.references);
        staleVars.add(v);
      } else if (refCount == firstRefAfterInit) {
        Reference reference = referenceInfo.references.get(
            firstRefAfterInit - 1);
        if (canInline(declaration, init, reference)) {
          inline(v, declaration, init, reference);
          staleVars.add(v);
        }
      } else if (declaration != init && refCount == 2) {
        if (isValidDeclaration(declaration) && isValidInitialization(init)) {
          Node value = init.getAssignedValue();
          Preconditions.checkNotNull(value);
          inlineWellDefinedVariable(v, value, referenceInfo.references);
          staleVars.add(v);
        }
      }
      if (!maybeModifiedArguments &&
          !staleVars.contains(v) &&
          referenceInfo.isWellDefined() &&
         !referenceInfo.isAssignedOnceInLifetime()) {
        List<Reference> refs = referenceInfo.references;
        for (int i = 1 /* start from a read */; i < refs.size(); i++) {
          Node nameNode = refs.get(i).getNode();
          if (aliasCandidates.containsKey(nameNode)) {
            AliasCandidate candidate = aliasCandidates.get(nameNode);
            if (!staleVars.contains(candidate.alias) &&
                !isVarInlineForbidden(candidate.alias)) {
              Reference aliasInit;
              aliasInit = candidate.refInfo.getInitializingReference();
              Node value = aliasInit.getAssignedValue();
              Preconditions.checkNotNull(value);
              inlineWellDefinedVariable(candidate.alias,
                  value,
                  candidate.refInfo.references);
              staleVars.add(candidate.alias);
            }
          }
        }
      }
    }
