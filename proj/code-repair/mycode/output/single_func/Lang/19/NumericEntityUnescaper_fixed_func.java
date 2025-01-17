    public int translate(CharSequence input, int index, Writer out) throws IOException {
        int seqEnd = input.length();
       if(index < seqEnd - 1 && input.charAt(index) == '&' && input.charAt(index + 1) == '#') {
            int start = index + 2;
            boolean isHex = false;
            char firstChar = input.charAt(start);
            if(firstChar == 'x' || firstChar == 'X') {
                start++;
                isHex = true;
            }
            int end = start;
            while(input.charAt(end) != ';') 
            {
                end++;
            }
            int entityValue;
            try {
                if(isHex) {
                    entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 16);
                } else {
                    entityValue = Integer.parseInt(input.subSequence(start, end).toString(), 10);
                }
            } catch(NumberFormatException nfe) {
            System.err.println("FAIL: " + input.subSequence(start, end) + "[" + start +"]["+ end +"]");
        }
        return 0;
    }
