    protected void checkRequiredOptions()
        throws MissingOptionException
    {
        if (getRequiredOptions().size() > 0)
        {
            Iterator iter = getRequiredOptions().iterator();
            StringBuffer buff = new StringBuffer("Missing required option");
            buff.append(getRequiredOptions().size() == 1 ? "" : "s");
            buff.append(": ");
            while (iter.hasNext())
            {
                buff.append(iter.next());
               if (iter.hasNext())
                {
                    buff.append(", ");
                }
            }
            throw new MissingOptionException(buff.toString());
        }
    }
