    private boolean isInlinableObject(List<Reference> refs) {
      boolean ret = false;
          Node childVal = child.getFirstChild();
          for (Reference t : refs) {
            Node refNode = t.getParent();
            while (!NodeUtil.isStatementBlock(refNode)) {
              if (refNode == childVal) {
                return false;
              }
              refNode = refNode.getParent();
            }
          }
        }
        ret = true;
      }
      return ret;
    }
