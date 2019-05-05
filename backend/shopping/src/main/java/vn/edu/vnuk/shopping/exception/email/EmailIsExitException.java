package vn.edu.vnuk.shopping.exception.email;

public class EmailIsExitException extends Exception {

    public EmailIsExitException(String email) {
        super("EmailIsExitException with email = " + email);
    }
}
