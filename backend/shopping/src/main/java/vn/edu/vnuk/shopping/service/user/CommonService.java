package vn.edu.vnuk.shopping.service.user;

import org.springframework.context.annotation.Lazy;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.exception.common.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;

@Lazy
public interface CommonService {

    void authenticate(String accessToken, HttpServletRequest request) throws TokenNotFoundException, UnauthorizedException, TokenIsExpiredException, AccountNotFoundException;

}
