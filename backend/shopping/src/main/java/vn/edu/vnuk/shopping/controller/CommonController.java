package vn.edu.vnuk.shopping.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vn.edu.vnuk.shopping.define.Define;
import vn.edu.vnuk.shopping.exception.account.AccountIsLockedException;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.account.AccountValidationException;
import vn.edu.vnuk.shopping.exception.email.EmailAndPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.model.OauthAccessToken;
import vn.edu.vnuk.shopping.repository.AccountRepository;
import vn.edu.vnuk.shopping.repository.OauthAccessTokenRepository;
import vn.edu.vnuk.shopping.service.user.MailClient;
import vn.edu.vnuk.shopping.service.user.TokenService;

import java.util.Date;

@RestController
public class CommonController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OauthAccessTokenRepository oauthAccessTokenRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClient mailClient;

    @PostMapping(value =  "/api/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@RequestBody Account account) throws EmailAndPasswordIsIncorrectException, AccountValidationException, AccountIsLockedException {

        return new ResponseEntity<>(tokenService.create(account), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> logout(@RequestParam("accessToken") String accessToken) throws TokenNotFoundException {
        tokenService.delete(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/forgot-password", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) throws AccountNotFoundException {
        Account account = accountRepository.getByEmail(email);
        if (account == null)
            throw new AccountNotFoundException(email);

        OauthAccessToken oauthAccessToken = new OauthAccessToken();
        oauthAccessToken.setAccessToken(DigestUtils.sha256Hex(account.getId() + account.getEmail() + (new Date().getTime())));
        oauthAccessToken.setExpires(Define.TIME_OF_TOKEN);
        oauthAccessToken.setExpiredAt(new Date((new Date()).getTime() + Define.TIME_OF_TOKEN * 1000));
        oauthAccessToken.setStatus(Define.STATUS_CREATED_TOKEN);
        oauthAccessToken.setAccountId(account.getId());
        oauthAccessToken.setCreatedAt(new Date());
        oauthAccessToken.setUpdatedAt(new Date());
        oauthAccessTokenRepository.save(oauthAccessToken);

        sendMailToResetPassword(account, oauthAccessToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Async
    void sendMailToResetPassword(Account account, OauthAccessToken oauthAccessToken) {
        Context context = new Context();
        context.setVariable("name", account.getFullname());
        context.setVariable("urlActivate", "http://localhost:8080/account/reset-password?token=" + oauthAccessToken.getAccessToken());
        String message = templateEngine.process("mailResetPasswordTemplate", context);

        mailClient.prepareAndSend(account.getEmail(), "Reset password TechShop account", message);
    }
}