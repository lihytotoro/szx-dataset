    public String absUrl(String attributeKey) {
        Validate.notEmpty(attributeKey);
    
        String relUrl = attr(attributeKey);
        if (!hasAttr(attributeKey)) {
            return ""; // nothing to make absolute with
        } else {
            URL base;
            try {
                base = new URL(baseUri);
            } catch (MalformedURLException e) {
                return "";
            }
            // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
            URL abs = new URL(base, relUrl);
            return abs.toExternalForm();
        }
    }
