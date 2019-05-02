package vn.edu.vnuk.shopping.exception;

public class EmailAndPasswordIsIncorrectException extends Exception {

    public EmailAndPasswordIsIncorrectException(String email, String password) {
        super("EmailAndPasswordIsIncorrectException with email = " + email + ", password = " + password);
    }
}
