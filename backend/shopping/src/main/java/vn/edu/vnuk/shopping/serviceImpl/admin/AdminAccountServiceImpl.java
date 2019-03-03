package vn.edu.vnuk.shopping.serviceImpl.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.vnuk.shopping.repository.AccountRepository;
import vn.edu.vnuk.shopping.service.admin.AdminAccountService;

@Service
public class AdminAccountServiceImpl implements AdminAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void deleteByEmail(String email) {
        accountRepository.deleteByEmail(email);
    }

    @Override
    public void deleteByID(Long id) {
        accountRepository.deleteById(id);
    }

}
