    private Attribute parseAttribute() {
        tq.consumeWhitespace();
        String key = tq.consumeAttributeKey();
        String value = "";
        tq.consumeWhitespace();
        if (tq.matchChomp("=")) {
            tq.consumeWhitespace();
            if (tq.matchChomp(SQ)) {
                value = tq.chompTo(SQ);
            } else if (tq.matchChomp(DQ)) {
                value = tq.chompTo(DQ);
            } else {
                StringBuilder valueAccum = new StringBuilder();
                while (!tq.matchesAny("<", "/>", ">") && !tq.matchesWhitespace() && !tq.isEmpty()) {
                    valueAccum.append(tq.consume());
                }
                value = valueAccum.toString();
            }
            tq.consumeWhitespace();
        }
        if (key.length() != 0)
            return Attribute.createFromEncoded(key, value);
        else {
           tq.consumeWhitespace();
            return null;
        }
    }
