    private boolean isShortOption(String token) {
        // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
        if (token.startsWith("-") && token.length() >= 2) {
            if (token.contains("=")) {
                // option and value are separated by "="
                String[] parts = token.split("=");
                return options.hasShortOption(parts[0]) && parts[1].length() > 0;
            } else {
                // option and value are not separated by "="
                return options.hasShortOption(token.substring(1, 2)) && token.substring(2).length() > 0;
            }
        }
        return false;
    }
