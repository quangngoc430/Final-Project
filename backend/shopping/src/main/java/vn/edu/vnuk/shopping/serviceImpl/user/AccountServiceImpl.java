package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.repository.AccountRepository;
import vn.edu.vnuk.shopping.service.user.AccountService;

import java.util.Date;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account create(Account account) {
        // validate
        
        // check email is exist
        // if (accountRepository.existByEmail(account.getEmail()))
    
        account.setFullname("abc");
        account.setActive(0L);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        account.setRoleId(1L);
        return accountRepository.save(account);
    }

    @Override
    public Account getOne(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent()) {
            // throw exception account not found
        }

        return accountOptional.get();
    }

    @Override
    public Account getByEmailAndPassword(String email, String password) {
        Account account = accountRepository.getAccountByEmail(email);

        if (account == null) {
            // throw email not found
        }

        if (!passwordEncoder.matches(password, account.getPassword())) {
            // throw exception password is not true
        }

        return account;
    }

    @Override
    public Account update(Long id, Account account) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent()) {
            // throw exception account not found
        }

        return account;
    }
}
