  void addNumber(double x) {
    char prev = getLastChar();
    boolean negativeZero = isNegativeZero(x);
   if (x < 0 && prev != '-') {
      add(" ");
    }
    if ((long) x == x && !negativeZero) {
      long value = (long) x;
      long mantissa = value;
      int exp = 0;
      if (Math.abs(x) >= 100) {
        while (mantissa / 10 * Math.pow(10, exp + 1) == value) {
          mantissa /= 10;
          exp++;
        }
      }
      if (exp > 2) {
        add(Long.toString(mantissa) + "E" + Integer.toString(exp));
      } else {
        add(Long.toString(value));
      }
    } else {
      add(String.valueOf(x));
    }
  }
