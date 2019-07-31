package vn.edu.vnuk.shopping.serviceImpl.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vn.edu.vnuk.shopping.define.Define;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.account.AccountPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.account.AccountValidationException;
import vn.edu.vnuk.shopping.exception.email.EmailIsExitException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.model.OauthAccessToken;
import vn.edu.vnuk.shopping.repository.AccountRepository;
import vn.edu.vnuk.shopping.repository.OauthAccessTokenRepository;
import vn.edu.vnuk.shopping.service.user.AccountService;
import vn.edu.vnuk.shopping.service.user.MailClient;
import vn.edu.vnuk.shopping.validation.account.AccountValidation;
import vn.edu.vnuk.shopping.validation.account.GroupCreateAccount;
import vn.edu.vnuk.shopping.validation.account.GroupUpdateAccount;
import vn.edu.vnuk.shopping.validation.account.GroupUpdateAccountPassword;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private AccountValidation accountValidation;

    @Autowired
    private OauthAccessTokenRepository oauthAccessTokenRepository;

    @Autowired
    private MailClient mailClient;

    @Override
    public Account create(Account account) throws EmailIsExitException, AccountValidationException {
        // validate
        accountValidation.validate(account, GroupCreateAccount.class);

        // check email is exist
        if (accountRepository.existsByEmail(account.getEmail()))
            throw new EmailIsExitException(account.getEmail());

        account.setStatus(Define.STATUS_DEACTIVE_ACCOUNT);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRoleId(Define.ROLE_NORMAL_USER);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());

        account = accountRepository.save(account);

        OauthAccessToken oauthAccessToken = new OauthAccessToken();
        oauthAccessToken.setAccessToken(DigestUtils.sha256Hex(account.getId() + account.getEmail() + (new Date().getTime())));
        oauthAccessToken.setExpires(Define.TIME_OF_TOKEN);
        oauthAccessToken.setExpiredAt(new Date((new Date()).getTime() + Define.TIME_OF_TOKEN * 1000));
        oauthAccessToken.setStatus(Define.STATUS_CREATED_TOKEN);
        oauthAccessToken.setAccountId(account.getId());
        oauthAccessToken.setCreatedAt(new Date());
        oauthAccessToken.setUpdatedAt(new Date());
        oauthAccessTokenRepository.save(oauthAccessToken);

        sendMailToActiveAccount(account, oauthAccessToken);

        return account;
    }

    @Async
    void sendMailToActiveAccount(Account account, OauthAccessToken oauthAccessToken) {
        Context context = new Context();
        context.setVariable("name", account.getFullname());
        context.setVariable("urlActivate", "http://localhost:8080/account/" + account.getId() + "/activate?token=" + oauthAccessToken.getAccessToken());
        String message = templateEngine.process("mailActivateTemplate", context);

        mailClient.prepareAndSend(account.getEmail(), "Activate your TechShop account", message);
    }

    @Override
    public Page<Account> getAll(String keyword, Pageable pageable) {
        return accountRepository.getAllByKeyword(keyword, pageable);
    }

    @Override
    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_NORMAL_USER')")
    public Account getOne(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent())
            throw new AccountNotFoundException(id);

        return accountOptional.get();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_NORMAL_USER') and (@accountServiceImpl.isAccountLogin(#id) == true))")
    public Account update(Long id, Account account) throws AccountNotFoundException, AccountValidationException, AccountPasswordIsIncorrectException {
        accountValidation.validate(account, GroupUpdateAccount.class);

        if (account.getPassword() != null) accountValidation.validate(account, GroupUpdateAccountPassword.class);

        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent()) throw new AccountNotFoundException(id);

        Account oldAccount = accountOptional.get();
        oldAccount.setFullname(account.getFullname());

        boolean isDeleteToken = false;

        if (account.getPassword() != null) {
            if (!passwordEncoder.matches(account.getPassword(), oldAccount.getPassword()))
                throw new AccountPasswordIsIncorrectException(account.getPassword());
            else {
                oldAccount.setPassword(passwordEncoder.encode(account.getNewPassword()));
                isDeleteToken = true;
            }
        } else {
          if (getAccountLogin().getRoleId() == Define.ROLE_ADMIN && account.getNewPassword() != null) {
              oldAccount.setPassword(passwordEncoder.encode(account.getNewPassword()));
              isDeleteToken = true;
          }
        }

        if (isDeleteToken) {
            oauthAccessTokenRepository.deleteAllByAccountId(oldAccount.getId());
        }

        oldAccount.setUpdatedAt(new Date());

        return accountRepository.save(oldAccount);
    }

    @Override
    public Account getAccountLogin() {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return accountRepository.getByEmail(email);
    }

    @Override
    public boolean isAccountLogin(long accountId) {
        Account account = getAccountLogin();
        return (account.getId() == accountId);
    }
}
