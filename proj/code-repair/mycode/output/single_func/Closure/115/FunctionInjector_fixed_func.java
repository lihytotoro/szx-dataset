  private CanInlineResult canInlineReferenceDirectly(
      Node callNode, Node fnNode) {
    if (!isDirectCallNodeReplacementPossible(fnNode)) {
      return CanInlineResult.NO;
    }
    Node block = fnNode.getLastChild();
          if (NodeUtil.mayEffectMutableState(cArg, compiler)
              && NodeUtil.getNameReferenceCount(
                  block, fnParam.getString()) > 1) {
            return CanInlineResult.NO;
          }
        }
        fnParam = fnParam.getNext();
      }
      if (cArg != null) {
        if (NodeUtil.mayHaveSideEffects(cArg, compiler)) {
          return CanInlineResult.NO;
        }
        cArg = cArg.getNext();
      }
    }
    return CanInlineResult.YES;
  }
