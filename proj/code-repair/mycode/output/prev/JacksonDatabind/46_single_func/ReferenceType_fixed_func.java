    public StringBuilder getGenericSignature(StringBuilder sb) {
        _classSignature(_class, sb, false);
        sb.append('<');
        if (_referencedType instanceof TypeVariable) {
            sb.append(((TypeVariable) _referencedType).getGenericSignature());
        } else {
            sb.append(_referencedType.getGenericSignature());
        }
        sb.append(';');
        return sb;
    }
