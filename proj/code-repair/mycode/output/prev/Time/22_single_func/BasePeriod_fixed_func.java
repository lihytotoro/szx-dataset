    protected BasePeriod(long duration) {
        this(duration, null, null);
        // bug [3264409]
        this.weeks = 0;
    }
