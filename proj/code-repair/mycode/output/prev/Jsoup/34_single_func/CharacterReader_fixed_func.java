    int nextIndexOf(CharSequence seq) {
        char startChar = seq.charAt(0);
        for (int offset = pos; offset < length; offset++) {
            if (startChar != input[offset]) {
                while (++offset < length && startChar != input[offset]) {
                    if (Character.isSurrogate(input[offset])) {
                        offset++;
                    }
                }
            }
            int i = offset + 1;
            int last = i + seq.length() - 1;
            if (offset < length) {
                for (int j = 1; i < last && seq.charAt(j) == input[i]; i++, j++) {
                    if (Character.isSurrogate(input[i])) {
                        i++;
                    }
                }
                if (i == last) { // found full sequence
                    return offset - pos;
                }
            }
        }
        return -1;
    }
