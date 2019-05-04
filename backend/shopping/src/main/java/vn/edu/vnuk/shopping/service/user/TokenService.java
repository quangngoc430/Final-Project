package vn.edu.vnuk.shopping.service.user;

import vn.edu.vnuk.shopping.exception.*;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.model.OauthAccessToken;

public interface TokenService {

    OauthAccessToken create(Account account) throws EmailAndPasswordIsIncorrectException, AccountValidationException, AccountIsLockedException;

    boolean isTokenExpired(OauthAccessToken oauthAccessToken);

    OauthAccessToken get(String accessToken) throws TokenNotFoundException, TokenIsExpiredException;

    void delete(String accessToken) throws TokenNotFoundException;
}
