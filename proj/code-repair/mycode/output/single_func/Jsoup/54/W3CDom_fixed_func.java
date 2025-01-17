        private void copyAttributes(org.jsoup.nodes.Node source, Element el) {
            for (Attribute attribute : source.attributes()) {
                String key = attribute.getKey().replaceAll("[^-a-zA-Z0-9_:.]", "");
               if (key.length() > 0)
                    el.setAttribute(key, attribute.getValue());
            }
        }
