    protected void processProperties(Properties properties)
    {
        if (properties == null)
        {
            return;
        }
        for (Enumeration e = properties.propertyNames(); e.hasMoreElements();)
        {
            String option = e.nextElement().toString();
            if (!cmd.hasOption(option))
            {
                Option opt = getOptions().getOption(option);
                String value = properties.getProperty(option);
                if (opt.hasArg())
                {
                    if (opt.getValues() == null || opt.getValues().length == 0)
                    {
                        try
                        {
                            opt.addValueForProcessing(value);
                        }
                        catch (RuntimeException exp)
                        {
                        }
                    }
                }
                else if (!("yes".equalsIgnoreCase(value)
                        || "true".equalsIgnoreCase(value)
                        || "1".equalsIgnoreCase(value)))
                {
                   continue;
                }
                cmd.addOption(opt);
            }
        }
    }
