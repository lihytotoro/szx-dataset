    private boolean isShortOption(String token) {
        // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
        if (!token.startsWith("-") || token.length() == 1) {
            return false;
        }
    
        // remove leading "-" and "=value"
        int pos = token.indexOf("=");
        String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);
    
        // check for concatenated short options
        if (optName.length() > 1) {
            for (int i = 0; i < optName.length(); i++) {
                if (!options.hasShortOption(optName.substring(i, i + 1))) {
                    return false;
                }
            }
            return true;
        }
    
        return options.hasShortOption(optName);
    }
