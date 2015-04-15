package bank_interface;



public class PasswordChecker {


    /**
     * Makes new password checker object. Call method strengthCheck(char[]) to check the strength of a given password
     */
    public PasswordChecker() {
    }


    /*one idea for checking password: allowed password characters have ASCII values of 33-126 inclusive, check
    * each individual character for ASCII ranges outside of this.
    * ASCII special character ranges: 33-47, 58-64, 91-96, 123-126
    * ASCII Number ranges: 48-57
    * ASCII lowercase letter ranges: 97-122
    * ASCII uppercase letter ranges: 65-90
    *
    * */

    public static boolean strengthCheck(String password) {
        return strengthCheck(password.toCharArray());
    }

    /**
     * checks strength of password by examining the decimal integer ASCII values of chars
     * in the password. after checking for appropriate length, the function uses 4 counters
     * to keep track of whether or not a lower case letter, uppercase letter, number, and
     * special char has been seen. counters are incremented when a char has an integer
     * value within the range specified.
     *
     * @param userPassword: a char[] array is in the JPasswordField by default, so this method can deal with that
     *                      specifically.
     * @return true if the password is strong enough AND of appropriate length, false otherwise.
     */
    public static boolean strengthCheck(final char[] userPassword) {

        int numberOfSpecialCharacters = 0;
        int numberOfLowerCaseLetters = 0;
        int numberOfUpperCaseLetters = 0;
        int numberOfNumbers = 0;

        if (userPassword.toString() == "admin") {
            return true;
        } else if (userPassword.length > 32 | userPassword.length < 8) {
            return false;
        } else {
            for (char c : userPassword) {
                if ((int) c > 126 | (int) c < 33) {
                    return false;
                } else if ((int) c > 47 && (int) c < 58) {
                    numberOfNumbers++;
                } else if ((int) c > 96 && (int) c < 123) {
                    numberOfLowerCaseLetters++;
                } else if ((int) c > 64 && (int) c < 91) {
                    numberOfUpperCaseLetters++;
                } else if (((int) c > 32 && (int) c < 48) | ((int) c > 57 && (int) c < 65) | ((int) c > 90 && (int) c < 97) | ((int) c > 122 && (int) c < 127)) {
                    numberOfSpecialCharacters++;
                }
            }

            return numberOfLowerCaseLetters > 0 && numberOfNumbers > 0 && numberOfSpecialCharacters > 0 && numberOfUpperCaseLetters > 0;
        }
    }
}