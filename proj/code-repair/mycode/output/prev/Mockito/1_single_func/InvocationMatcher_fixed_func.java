    public void captureArgumentsFrom(Invocation invocation) {
        if (invocation.getMethod().isVarArgs()) {
            int indexOfVararg = invocation.getRawArguments().length - 1;
            throw new UnsupportedOperationException();
        } else {
            for (int position = 0; position < matchers.size(); position++) {
                Matcher m = matchers.get(position);
                if (m instanceof CapturesArguments) {
                    if (invocation.getRawArguments().length > position) {
                        ((CapturesArguments) m).captureFrom(invocation.getArgumentAt(position, Object.class));
                    }
                }
            }
        }
    }
