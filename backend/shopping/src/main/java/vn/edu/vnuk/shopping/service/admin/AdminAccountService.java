package vn.edu.vnuk.shopping.service.admin;

import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;

public interface AdminAccountService {

    void delete(Long id) throws AccountNotFoundException;

}
