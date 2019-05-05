package vn.edu.vnuk.shopping.serviceImpl.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.define.Define;
import vn.edu.vnuk.shopping.exception.account.AccountIsLockedException;
import vn.edu.vnuk.shopping.exception.account.AccountValidationException;
import vn.edu.vnuk.shopping.exception.email.EmailAndPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.model.OauthAccessToken;
import vn.edu.vnuk.shopping.repository.AccountRepository;
import vn.edu.vnuk.shopping.repository.OauthAccessTokenRepository;
import vn.edu.vnuk.shopping.service.user.TokenService;
import vn.edu.vnuk.shopping.validation.Account.AccountValidation;
import vn.edu.vnuk.shopping.validation.GroupLoginAccount;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private OauthAccessTokenRepository oauthAccessTokenRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountValidation accountValidation;

    @Transactional
    @Override
    public OauthAccessToken create(Account accountParam) throws EmailAndPasswordIsIncorrectException, AccountValidationException, AccountIsLockedException {
        accountValidation.validate(accountParam, GroupLoginAccount.class);

        boolean isCorrect = true;
        String email = accountParam.getEmail(), password = accountParam.getPassword();

        Account account = accountRepository.getByEmail(email);

        if (account == null) isCorrect = false;

        if (!passwordEncoder.matches(password, account.getPassword())) isCorrect = false;

        if (!isCorrect) throw new EmailAndPasswordIsIncorrectException(email, password);

        if (account.getStatus() == Define.STATUS_DELETED_ACCOUNT) throw new AccountIsLockedException(account.getId());

        OauthAccessToken oauthAccessToken = new OauthAccessToken();
        oauthAccessToken.setAccessToken(DigestUtils.sha256Hex(account.getId() + account.getEmail() + (new Date().getTime())));
        oauthAccessToken.setExpires(Define.TIME_OF_TOKEN);
        oauthAccessToken.setExpiredAt(new Date((new Date()).getTime() + Define.TIME_OF_TOKEN * 1000));
        oauthAccessToken.setStatus(Define.STATUS_CREATED_TOKEN);
        oauthAccessToken.setAccountId(account.getId());

        oauthAccessToken = oauthAccessTokenRepository.save(oauthAccessToken);
        oauthAccessToken.setAccount(accountRepository.findById(oauthAccessToken.getAccountId()).get());

        return oauthAccessToken;
    }

    @Override
    public boolean isTokenExpired(OauthAccessToken oauthAccessToken) {
        return (new Date()).getTime() >= oauthAccessToken.getExpiredAt().getTime();
    }

    @Override
    public OauthAccessToken get(String accessToken) throws TokenNotFoundException, TokenIsExpiredException {
        OauthAccessToken oauthAccessToken = oauthAccessTokenRepository.getByAccessToken(accessToken);

        if (oauthAccessToken == null) throw new TokenNotFoundException(accessToken);

        if (isTokenExpired(oauthAccessToken)) throw new TokenIsExpiredException(accessToken);

        return oauthAccessToken;
    }

    @Override
    public void delete(String accessToken) throws TokenNotFoundException {
        OauthAccessToken oauthAccessToken = oauthAccessTokenRepository.getByAccessToken(accessToken);

        if (oauthAccessToken == null) throw new TokenNotFoundException(accessToken);

        oauthAccessToken.setStatus(Define.STATUS_DELETED_TOKEN);
        oauthAccessTokenRepository.save(oauthAccessToken);
    }

}
