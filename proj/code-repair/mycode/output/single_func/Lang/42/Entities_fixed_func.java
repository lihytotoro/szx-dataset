    public void escape(Writer writer, String str) throws IOException {
        int len = str.length();
        for (int i = 0; i < len; i++) {
           char c = str.charAt(i);
            if (c > 0x7F) {
                String entityName = this.entityName(c);
                if (entityName == null) {
                    writer.write("&#");
                    writer.write(Integer.toString(c, 10));
                    writer.write(';');
                } else {
                    writer.write(c);
                }
            } else {
                writer.write('&');
                writer.write(entityName);
                writer.write(';');
            }
        }
    }
