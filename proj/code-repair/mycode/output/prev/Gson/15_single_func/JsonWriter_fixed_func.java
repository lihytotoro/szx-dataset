  public JsonWriter value(double value) throws IOException {
    writeDeferredName();
    beforeValue();
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      out.append(Double.toString(value));
    } else {
      out.append(Double.toString(value));
    }
    return this;
  }
