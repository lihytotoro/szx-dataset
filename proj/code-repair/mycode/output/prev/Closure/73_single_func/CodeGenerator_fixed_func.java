  private static void appendHexJavaScriptRepresentation(StringBuilder sb, char c) {
      sb.append("\\u");
      sb.append(Integer.toHexString(c));
  }