package vn.edu.vnuk.shopping.exception;

public class TokenIsExpiredException extends Exception {

    public TokenIsExpiredException(String token){
        super("TokenIsExpiredException with token = " + token);
    }
}
