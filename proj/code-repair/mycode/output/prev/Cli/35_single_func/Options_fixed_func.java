    public List<String> getMatchingOptions(String opt) {
        opt = Util.stripLeadingHyphens(opt);
    
        List<String> matchingOpts = new ArrayList<String>();
    
        // for a perfect match return the single option only
        if (longOpts.containsKey(opt)) {
            matchingOpts.add(opt);
        } else {
            // for a prefix match return all options that start with the given string
            for (String longOpt : longOpts.keySet()) {
                if (longOpt.startsWith(opt)) {
                    matchingOpts.add(longOpt);
                }
            }
        }
    
        return matchingOpts;
    }
