    final void html(final Appendable accum, final Document.OutputSettings out) throws IOException {
        final int sz = size;
        for (int i = 0; i < sz; i++) {
            final String key = keys[i];
            final String val = vals[i];
            accum.append(' ').append(key);
           if (!(out.syntax() == Document.OutputSettings.Syntax.html && (val == null || val.equals(key) && Attribute.isBooleanAttribute(key)))) {
                accum.append("=\"");
                Entities.escape(accum, val == null ? EmptyString : val, out, true, false, false);
                accum.append('"');
            }
        }
    }
