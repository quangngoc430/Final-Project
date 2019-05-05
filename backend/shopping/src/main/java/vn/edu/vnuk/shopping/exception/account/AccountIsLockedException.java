package vn.edu.vnuk.shopping.exception.account;

public class AccountIsLockedException extends Exception {
    public AccountIsLockedException(Long id) {
        super("AccountIsLockedException with id = " + id);
    }
}
