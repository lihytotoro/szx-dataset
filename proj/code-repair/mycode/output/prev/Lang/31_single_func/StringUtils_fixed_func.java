 public static boolean containsAny(CharSequence cs, char[] searchChars) {
     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
         return false;
     }
     int csLength = cs.length();
     int searchLength = searchChars.length;
     for (int i = 0; i < csLength; i++) {
         char ch = cs.charAt(i);
         if (ch < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
             for (int j = 0; j < searchLength; j++) {
                 if (searchChars[j] == ch) {
                     return true;
                 }
             }
         } else {
             for (int j = 0; j < searchLength; j++) {
                 if (searchChars[j] == ch || searchChars[j] == Character.toLowerCase(ch)) {
                     return true;
                 }
             }
         }
     }
     return false;
 }
