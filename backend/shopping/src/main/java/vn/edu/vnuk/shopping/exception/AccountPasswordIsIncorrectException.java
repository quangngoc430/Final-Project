package vn.edu.vnuk.shopping.exception;

public class AccountPasswordIsIncorrectException extends Exception {

    public AccountPasswordIsIncorrectException(String message){
        super("AccountPasswordIsIncorrect with message = " + message);
    }
}
