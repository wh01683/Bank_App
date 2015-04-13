package bank_interface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;

    public EmailValidator() {
        this.pattern = Pattern.compile(EMAIL_PATTERN);
    }


    /** Validate email with regular expression

     @param email email string for validation
     @return true valid email, false invalid email

      */

    public boolean validate(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}

