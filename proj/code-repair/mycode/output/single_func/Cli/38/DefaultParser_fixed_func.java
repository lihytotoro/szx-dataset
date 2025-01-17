    private boolean isShortOption(String token)
    {
        if (!token.startsWith("-") || token.length() == 1)
        {
            return false;
        }
        int pos = token.indexOf("=");
        String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);
       return optName.length() == 1 && options.hasShortOption(optName);
    }
