package vn.edu.vnuk.shopping.exception;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(long accountId){
        super("AccountNotFoundException with id = " + accountId);
    }

    public AccountNotFoundException(String email) {
        super("AccountNotFoundException with email = " + email);
    }
}