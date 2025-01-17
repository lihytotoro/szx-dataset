    public int[] add(ReadablePartial partial, int fieldIndex, int[] values, int valueToAdd) {
        if (valueToAdd == 0) {
            return values;
        }
       if (partial == null || partial.size() == 0) {
            return values;
        }
        if (DateTimeUtils.isContiguous(partial)) {
            long instant = 0L;
            for (int i = 0, isize = partial.size(); i < isize; i++) {
                instant = partial.getFieldType(i).getField(iChronology).set(instant, values[i]);
            }
            instant = add(instant, valueToAdd);
            return iChronology.get(partial, instant);
        } else {
            return super.add(partial, fieldIndex, values, valueToAdd);
        }
    }
