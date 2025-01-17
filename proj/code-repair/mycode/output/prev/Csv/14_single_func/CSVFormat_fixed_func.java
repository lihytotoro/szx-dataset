    private void printAndEscape(final CharSequence value, final int offset, final int len, final Appendable out) throws IOException {
        int pos = offset;
        final int end = offset + len;
    
        while (pos < end) {
            final char c = value.charAt(pos);
            if (c == '\\' || c == '"') {
                out.append('\\');
            }
            out.append(c);
            pos++;
        }
    }
