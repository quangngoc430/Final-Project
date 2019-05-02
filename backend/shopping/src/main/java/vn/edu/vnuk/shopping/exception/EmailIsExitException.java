package vn.edu.vnuk.shopping.exception;

public class EmailIsExitException extends Exception {

    public EmailIsExitException(String email) {
        super("EmailIsExitException with email = " + email);
    }
}
