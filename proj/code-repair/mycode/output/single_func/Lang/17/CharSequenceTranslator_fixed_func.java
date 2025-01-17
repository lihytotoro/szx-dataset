    public final void translate(CharSequence input, Writer out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }
        if (input == null) {
            return;
        }
        int pos = 0;
       while (pos < input.length()) {
            int consumed = translate(input, pos, out);
            if (consumed == 0) {
                char[] c = Character.toChars(Character.codePointAt(input, pos));
                out.write(c);
            }
            else {
            for (int pt = 0; pt < consumed; pt++) {
                    if (pos < input.length() - 2) {
                pos += Character.charCount(Character.codePointAt(input, pos));
                    } else {
                        pos++;
                    }
                }
                pos--;
            }
            pos++;
        }
    }
