package vn.edu.vnuk.shopping.service.user;

import vn.edu.vnuk.shopping.exception.account.AccountIsLockedException;
import vn.edu.vnuk.shopping.exception.account.AccountValidationException;
import vn.edu.vnuk.shopping.exception.email.EmailAndPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.model.OauthAccessToken;

public interface TokenService {

    OauthAccessToken create(Account account) throws EmailAndPasswordIsIncorrectException, AccountValidationException, AccountIsLockedException;

    boolean isTokenExpired(OauthAccessToken oauthAccessToken);

    OauthAccessToken get(String accessToken) throws TokenNotFoundException, TokenIsExpiredException;

    void delete(String accessToken) throws TokenNotFoundException;
}
