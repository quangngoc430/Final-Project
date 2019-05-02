package vn.edu.vnuk.shopping.exception;

public class AccountIsExistException extends Exception{

    public AccountIsExistException(String email){
        super("AccountIsExistException with email = " + email);
    }
}
