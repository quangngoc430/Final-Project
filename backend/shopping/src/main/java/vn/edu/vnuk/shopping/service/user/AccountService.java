package vn.edu.vnuk.shopping.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.vnuk.shopping.exception.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.AccountPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.AccountValidationException;
import vn.edu.vnuk.shopping.exception.EmailIsExitException;
import vn.edu.vnuk.shopping.model.Account;

public interface AccountService {

    Account create(Account account) throws EmailIsExitException, AccountValidationException;

    Page<Account> getAll(String keyword, Pageable pageable);

    Account getOne(Long id) throws AccountNotFoundException;

    Account update(Long id, Account account) throws AccountNotFoundException, AccountValidationException, AccountPasswordIsIncorrectException;

    void delete(Long id) throws AccountNotFoundException;
}
