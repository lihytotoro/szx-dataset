    protected double acceptStep(final AbstractStepInterpolator interpolator,
                                final double[] y, final double[] yDot, final double tEnd)
        throws MathIllegalStateException {
            double previousT = interpolator.getGlobalPreviousTime();
            final double currentT = interpolator.getGlobalCurrentTime();
                    return eventT;
                }
                previousT = eventT;
                interpolator.setSoftPreviousTime(eventT);
                interpolator.setSoftCurrentTime(currentT);
                if (currentEvent.evaluateStep(interpolator)) {
                    occuringEvents.add(currentEvent);
                }
            }
            interpolator.setInterpolatedTime(currentT);
            final double[] currentY = interpolator.getInterpolatedState();
            for (final EventState state : eventsStates) {
                state.stepAccepted(currentT, currentY);
                isLastStep = isLastStep || state.stop();
            }
            isLastStep = isLastStep || Precision.equals(currentT, tEnd, 1);
            for (StepHandler handler : stepHandlers) {
                handler.handleStep(interpolator, isLastStep);
            }
            return currentT;
    }
