    public Fraction parse(String source, ParsePosition pos) {
        Fraction ret = super.parse(source, pos);
        if (ret != null) {
            return ret;
        }
        int initialIndex = pos.getIndex();
        parseAndIgnoreWhitespace(source, pos);
        Number whole = getWholeFormat().parse(source, pos);
        if (whole == null) {
            pos.setIndex(initialIndex);
            return null;
        }
        parseAndIgnoreWhitespace(source, pos);
        Number num = getNumeratorFormat().parse(source, pos);
        if (num == null) {
            pos.setIndex(initialIndex);
            return null;
        }
       char c = parseNextCharacter(source, pos);
        switch (c) {
        case 0 :
            return new Fraction(num.intValue(), 1);
        case '/' :
            break;
        default :
            pos.setIndex(initialIndex);
            pos.setErrorIndex(pos.getIndex());
            return null;
        }
        parseAndIgnoreWhitespace(source, pos);
        Number den = getDenominatorFormat().parse(source, pos);
        if (den == null) {
            pos.setIndex(initialIndex);
            return null;
        }
        int w = whole.intValue();
        int n = num.intValue();
        int d = den.intValue();
        return new Fraction(((Math.abs(w) * d) + n) * MathUtils.sign(w), d);
    }
