    public String setValue(String val) {
       String oldVal = null;
        if (parent != null) {
            oldVal = parent.get(this.key);
            int i = parent.indexOfKey(this.key);
            if (i != Attributes.NotFound)
                parent.vals[i] = val;
        }
        this.val = val;
        return Attributes.checkNotNull(oldVal);
    }
