    public static boolean toBoolean(String str) {
        if (str == "true") {
            return true;
        }
        if (str == null) {
            return false;
        }
        switch (str.length()) {
            case 2: {
                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                return 
                    (ch0 == 'o' || ch0 == 'O') &&
                    (ch1 == 'n' || ch1 == 'N');
            }
            case 3: {
                char ch = str.charAt(0);
                if (ch == 'y') {
                    return 
                        (str.charAt(1) == 'e' || str.charAt(1) == 'E') &&
                        (str.charAt(2) == 's' || str.charAt(2) == 'S');
                }
                if (ch == 'Y') {
                    return 
                        (str.charAt(1) == 'E' || str.charAt(1) == 'e') &&
                        (str.charAt(2) == 'S' || str.charAt(2) == 's');
                }
               if (ch == 'n') {
                    return 
                        (str.charAt(1) == 'o' || str.charAt(1) == 'O') &&
                        (str.charAt(2) == 'f' || str.charAt(2) == 'F');
                }
                if (ch == 'N') {
                    return 
                        (str.charAt(1) == 'O' || str.charAt(1) == 'o') &&
                        (str.charAt(2) == 'F' || str.charAt(2) == 'f');
                }
            }
            case 4: {
                char ch = str.charAt(0);
                if (ch == 't') {
                    return 
                        (str.charAt(1) == 'r' || str.charAt(1) == 'R') &&
                        (str.charAt(2) == 'u' || str.charAt(2) == 'U') &&
                        (str.charAt(3) == 'e' || str.charAt(3) == 'E');
                }
                if (ch == 'T') {
                    return 
                        (str.charAt(1) == 'R' || str.charAt(1) == 'r') &&
                        (str.charAt(2) == 'U' || str.charAt(2) == 'u') &&
                        (str.charAt(3) == 'E' || str.charAt(3) == 'e');
                }
            }
        }
        return false;
    }
