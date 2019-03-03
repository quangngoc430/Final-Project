package vn.edu.vnuk.shopping.service.user;

import vn.edu.vnuk.shopping.model.Account;

public interface AccountService {

    Account create(Account account);

    Account getOne(Long id);

    Account getByEmail(String email);

    Account getByEmailAndPassword(String email, String password);

    Account update(Long id, Account account);

}
