    private void printAndQuote(final Object object, final CharSequence value, final int offset, final int len,
            final Appendable out, final boolean newRecord) throws IOException {
        boolean quote = false;
        int start = offset;
        int pos = offset;
        final int end = offset + len;
        final char delimChar = getDelimiter();
        final char quoteChar = getQuoteCharacter().charValue();
        QuoteMode quoteModePolicy = getQuoteMode();
        if (quoteModePolicy == null) {
            quoteModePolicy = QuoteMode.MINIMAL;
        }
        switch (quoteModePolicy) {
        case ALL:
            quote = true;
            break;
        case NON_NUMERIC:
            quote = !(object instanceof Number);
            break;
        case NONE:
            printAndEscape(value, offset, len, out);
            return;
        case MINIMAL:
            if (len <= 0) {
                if (newRecord) {
                    quote = true;
                }
            } else {
                char c = value.charAt(pos);
               if (newRecord && (c < '0' || c > '9' && c < 'A' || c > 'Z' && c < 'a' || c > 'z')) {
                    quote = true;
                } else if (c <= COMMENT) {
                    quote = true;
                } else {
                    while (pos < end) {
                        c = value.charAt(pos);
                        if (c == LF || c == CR || c == quoteChar || c == delimChar) {
                            quote = true;
                            break;
                        }
                        pos++;
                    }
                    if (!quote) {
                        pos = end - 1;
                        c = value.charAt(pos);
                        if (c <= SP) {
                            quote = true;
                        }
                    }
                }
            }
            if (!quote) {
                out.append(value, start, end);
                return;
            }
            break;
        default:
            throw new IllegalStateException("Unexpected Quote value: " + quoteModePolicy);
        }
        if (!quote) {
            out.append(value, start, end);
            return;
        }
        out.append(quoteChar);
        while (pos < end) {
            final char c = value.charAt(pos);
            if (c == quoteChar) {
                out.append(value, start, pos + 1);
                start = pos;
            }
            pos++;
        }
        out.append(value, start, pos);
        out.append(quoteChar);
    }
