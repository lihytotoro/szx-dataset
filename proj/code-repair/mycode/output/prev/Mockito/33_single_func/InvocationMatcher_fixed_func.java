    public boolean hasSameMethod(Invocation candidate) {
        Method m1 = invocation.getMethod();
        Method m2 = candidate.getMethod();
        return m1.equals(m2);
    }
