  @Override
  public void skipValue() throws IOException {
    if (peek() == JsonToken.NAME) {
      nextName();
      if (stackSize > 0) {
        pathNames[stackSize - 2] = "null";
      }
    } else {
      popStack();
      if (stackSize > 0) {
        pathNames[stackSize - 1] = "null";
      }
    }
    pathIndices[stackSize - 1]++;
  }
