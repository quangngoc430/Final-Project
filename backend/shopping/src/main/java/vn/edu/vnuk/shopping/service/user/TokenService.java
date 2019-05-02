package vn.edu.vnuk.shopping.service.user;

import vn.edu.vnuk.shopping.exception.AccountIsLockedException;
import vn.edu.vnuk.shopping.exception.AccountValidationException;
import vn.edu.vnuk.shopping.exception.EmailAndPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.model.OauthAccessToken;

public interface TokenService {

    OauthAccessToken create(Account account) throws EmailAndPasswordIsIncorrectException, AccountValidationException, AccountIsLockedException;

    boolean isTokenExpired(OauthAccessToken oauthAccessToken);

    OauthAccessToken get(String accessToken) throws TokenNotFoundException;

    void delete(String accessToken) throws TokenNotFoundException;
}
