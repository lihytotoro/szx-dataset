    public Object answer(InvocationOnMock invocation) {
        if (methodsGuru.isToString(invocation.getMethod())) {
            Object mock = invocation.getMock();
            MockName name = mockUtil.getMockName(mock);
            if (name.isDefault()) {
                return "Mock for " + mockUtil.getMockSettings(mock).getTypeToMock().getSimpleName() + ", hashCode: " + mock.hashCode();
            } else {
                return name.toString();
            }
        } else if (methodsGuru.isCompareToMethod(invocation.getMethod())) {
            //see issue 184.
            //mocks by default should return 0 if references are the same, otherwise some other value because they are not the same. Hence we return 0 (if references are the same) or 1 (if references are not the same).
            Object mock = invocation.getMock();
            Object other = invocation.getArguments()[0];
            if (mock == other) {
                return 0;
            } else {
                return 1;
            }
        }
    
        Class<?> returnType = invocation.getMethod().getReturnType();
        return returnValueFor(returnType);
    }
