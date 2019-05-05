package vn.edu.vnuk.shopping.exception.common;

public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super("BadRequestException with message = " + message);
    }
}
