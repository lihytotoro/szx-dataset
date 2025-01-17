    public void validate(final WriteableCommandLine commandLine)
        throws OptionException {
        int present = 0;
        Option unexpected = null;
        for (final Iterator i = options.iterator(); i.hasNext();) {
            final Option option = (Option) i.next();
            boolean validate = option.isRequired() || option instanceof Group;
           if (commandLine.hasOption(option)) {
                if (++present > maximum) {
                    unexpected = option;
                    break;
                }
                if (validate) {
                option.validate(commandLine);
            }
        }
        if (unexpected != null) {
            throw new OptionException(this, ResourceConstants.UNEXPECTED_TOKEN,
                                      unexpected.getPreferredName());
        }
        if (present < minimum) {
            throw new OptionException(this, ResourceConstants.MISSING_OPTION);
        }
        for (final Iterator i = anonymous.iterator(); i.hasNext();) {
            final Option option = (Option) i.next();
            option.validate(commandLine);
        }
    }
