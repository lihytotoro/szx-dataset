    public List<Connection.KeyVal> formData() {
        ArrayList<Connection.KeyVal> data = new ArrayList<Connection.KeyVal>();
        for (Element el: elements) {
            if (!el.tag().isFormSubmittable()) continue; // contents are form listable, superset of submitable
               String val = el.val();
                if (val.length() == 0) continue;
                String type = el.attr("type");
                if ("select".equals(el.tagName())) {
                    Elements options = el.select("option[selected]");
                    boolean set = false;
                    for (Element option: options) {
                        data.add(HttpConnection.KeyVal.create(name, option.val()));
                        set = true;
                    }
                    if (!set) {
                        Element option = el.select("option").first();
                        if (option != null)
                            data.add(HttpConnection.KeyVal.create(name, option.val()));
                    }
                } else if ("checkbox".equalsIgnoreCase(type) || "radio".equalsIgnoreCase(type)) {
                    if (el.hasAttr("checked")) {
                    data.add(HttpConnection.KeyVal.create(name, val));
                }
            } else {
                data.add(HttpConnection.KeyVal.create(name, el.val()));
            }
        }
        return data;
    }
