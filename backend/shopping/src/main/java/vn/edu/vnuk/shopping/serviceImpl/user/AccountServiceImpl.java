package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.service.user.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public Account create(Account account) {
        return null;
    }

    @Override
    public Account getOne(Long id) {
        return null;
    }

    @Override
    public Account getByEmail(String email) {
        return null;
    }

    @Override
    public Account getByEmailAndPassword(String email, String password) {
        return null;
    }

    @Override
    public Account update(Long id, Account account) {
        return null;
    }
}
