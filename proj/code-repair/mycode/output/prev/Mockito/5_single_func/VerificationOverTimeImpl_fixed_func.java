    public void verify(VerificationData data) {
        AssertionError error = null;
    
        timer.start();
        while (timer.isCounting()) {
            try {
                delegate.verify(data);
    
                if (returnOnSuccess) {
                    return;
                } else {
                    error = null;
                }
            } catch (MockitoAssertionError e) {
                error = handleVerifyException(e);
            }
            catch (org.mockito.exceptions.verification.junit.ArgumentsAreDifferent e) {
                throw new org.mockito.exceptions.verification.junit.ArgumentsAreDifferent(e);
            }
        }
    
        if (error != null) {
            throw error;
        }
    }
