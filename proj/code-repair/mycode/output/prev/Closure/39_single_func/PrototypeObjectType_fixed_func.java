  String toStringHelper(boolean forAnnotations) {
    if (hasReferenceName()) {
      return getReferenceName();
    } else if (forAnnotations) {
      // Don't pretty print recursively.
      boolean oldPrettyPrint = prettyPrint;
      prettyPrint = false;
  
      // Use a tree set so that the properties are sorted.
      Set<String> propertyNames = Sets.newTreeSet();
      for (ObjectType current = this;
           current != null && !current.isNativeObjectType() &&
               propertyNames.size() <= MAX_PRETTY_PRINTED_PROPERTIES;
           current = current.getImplicitPrototype()) {
        propertyNames.addAll(current.getOwnPropertyNames());
      }
  
      StringBuilder sb = new StringBuilder();
      sb.append("{");
  
      int i = 0;
      for (String property : propertyNames) {
        if (i > 0) {
          sb.append(", ");
        }
  
        sb.append(property);
        sb.append(": ");
        sb.append(getPropertyType(property).toString());
  
        ++i;
        if (i == MAX_PRETTY_PRINTED_PROPERTIES) {
          sb.append(", ...");
          break;
        }
      }
  
      sb.append("}");
  
      prettyPrint = oldPrettyPrint;
      return sb.toString();
    } else {
      return "{...}";
    }
  }
