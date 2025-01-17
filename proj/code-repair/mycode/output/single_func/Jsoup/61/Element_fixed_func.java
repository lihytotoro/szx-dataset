    public boolean hasClass(String className) {
       final String classAttr = attributes.get("class", "");
        final int len = classAttr.length();
        final int wantLen = className.length();
        if (len == 0 || len < wantLen) {
            return false;
        }
        if (len == wantLen) {
            return className.equalsIgnoreCase(classAttr);
        }
        boolean inClass = false;
        int start = 0;
        for (int i = 0; i < len; i++) {
            if (Character.isWhitespace(classAttr.charAt(i))) {
                if (inClass) {
                    if (i - start == wantLen && classAttr.regionMatches(true, start, className, 0, wantLen)) {
                        return true;
                    }
                    inClass = false;
                }
            } else {
                if (!inClass) {
                    inClass = true;
                    start = i;
                }
            }
        }
        if (inClass && len - start == wantLen) {
            return classAttr.regionMatches(true, start, className, 0, wantLen);
        }
        return false;
    }
