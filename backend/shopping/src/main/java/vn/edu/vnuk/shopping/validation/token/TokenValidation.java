package vn.edu.vnuk.shopping.validation.token;

import vn.edu.vnuk.shopping.exception.account.AccountValidationException;
import vn.edu.vnuk.shopping.model.OauthAccessToken;

public interface TokenValidation {
    void validate(OauthAccessToken oauthAccessToken, Class groupClassValidation) throws AccountValidationException;
}
