    public static String newStringIso8859_1(final byte[] bytes) {
    return new String(bytes, Charset.forName("ISO-8859-1"));
    }
