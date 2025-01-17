    protected String[] flatten(Options options, String[] arguments, boolean stopAtNonOption)
    {
        init();
        this.options = options;
        Iterator iter = Arrays.asList(arguments).iterator();
        while (iter.hasNext())
        {
            String token = (String) iter.next();
            if (token.startsWith("--"))
            {
               int equals = token.indexOf('=');
                if (equals != -1)
                {
                    tokens.add(token.substring(0, equals));
                    tokens.add(token.substring(equals + 1, token.length()));
                }
                else
                {
                    tokens.add(token);
                }
            }
            else if ("-".equals(token))
            {
                tokens.add(token);
            }
            else if (token.startsWith("-"))
            {
                if (token.length() == 2)
                {
                    processOptionToken(token, stopAtNonOption);
                }
                else if (options.hasOption(token))
                {
                    tokens.add(token);
                }
                else
                {
                    burstToken(token, stopAtNonOption);
                }
            }
            else if (stopAtNonOption)
            {
                process(token);
            }
            else
            {
                tokens.add(token);
            }
            gobble(iter);
        }
        return (String[]) tokens.toArray(new String[tokens.size()]);
    }
