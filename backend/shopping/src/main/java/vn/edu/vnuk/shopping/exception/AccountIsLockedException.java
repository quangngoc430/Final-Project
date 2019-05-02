package vn.edu.vnuk.shopping.exception;

public class AccountIsLockedException extends Exception {
    public AccountIsLockedException(Long id) {
        super("AccountIsLockedException with id = " + id);
    }
}
