package vn.edu.vnuk.shopping.serviceImpl.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import vn.edu.vnuk.shopping.define.Define;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.repository.AccountRepository;
import vn.edu.vnuk.shopping.service.admin.AdminAccountService;

import java.util.Optional;

@Service
public class AdminAccountServiceImpl implements AdminAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent())
            throw new AccountNotFoundException(id);

        Account account = accountOptional.get();
        account.setStatus(Define.STATUS_DELETED_ACCOUNT);

        accountRepository.save(account);
    }

}
