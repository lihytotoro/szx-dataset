  private void inferPropertyTypesToMatchConstraint(
      JSType type, JSType constraint) {
    if (type == null || constraint == null) {
      return;
    }
  
    ObjectType constraintObj =
        ObjectType.cast(constraint.restrictByNotNullOrUndefined());
  if (constraintObj != null && constraintObj.isRecordType()) {
    }
  }
