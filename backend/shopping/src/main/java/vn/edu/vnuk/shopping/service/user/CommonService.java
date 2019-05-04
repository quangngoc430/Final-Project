package vn.edu.vnuk.shopping.service.user;

import org.springframework.context.annotation.Lazy;
import vn.edu.vnuk.shopping.exception.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.TokenNotFoundException;
import vn.edu.vnuk.shopping.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;


@Lazy
public interface CommonService {

    void authenticate(String accessToken, HttpServletRequest request) throws TokenNotFoundException, UnauthorizedException, TokenIsExpiredException, AccountNotFoundException;

}
