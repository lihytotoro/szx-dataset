    public final Object computeValue(EvalContext context) {
        Object arg1 = args[0].computeValue(context);
        Object arg2 = args[1].computeValue(context);
        if (arg1 instanceof NodeSet && arg2 instanceof Number) {
            // Handle the case where one of the arguments is a node-set and the other is a number
            // by converting the node-set to a number and then comparing the two values
            Number num = ((NodeSet) arg1).getNumber();
            return compute(num, arg2) ? Boolean.TRUE : Boolean.FALSE;
        } else {
            return compute(arg1, arg2) ? Boolean.TRUE : Boolean.FALSE;
        }
    }
