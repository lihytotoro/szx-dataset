    private char getMappingCode(final String str, final int index) {
        final char mappedChar = this.map(str.charAt(index));
        if (index > 1 && mappedChar != '0') {
           final char hwChar = str.charAt(index - 2);
            if ('H' == hwChar || 'W' == hwChar) {
                final char preHWChar = str.charAt(index - 3);
                final char firstCode = this.map(preHWChar);
                if (firstCode == mappedChar || 'H' == preHWChar || 'W' == preHWChar) {
                    return 0;
                }
            }
        }
        return mappedChar;
    }
