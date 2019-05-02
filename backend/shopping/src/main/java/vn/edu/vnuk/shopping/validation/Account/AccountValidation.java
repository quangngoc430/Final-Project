package vn.edu.vnuk.shopping.validation.Account;

import vn.edu.vnuk.shopping.exception.AccountValidationException;
import vn.edu.vnuk.shopping.model.Account;

public interface AccountValidation {

    void validate(Account account, Class groupClassValidation) throws AccountValidationException;
}
