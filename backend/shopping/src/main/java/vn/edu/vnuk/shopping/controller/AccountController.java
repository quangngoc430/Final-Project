package vn.edu.vnuk.shopping.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.account.AccountPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.account.AccountValidationException;
import vn.edu.vnuk.shopping.exception.common.UnauthorizedException;
import vn.edu.vnuk.shopping.exception.email.EmailIsExitException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.service.admin.AdminAccountService;
import vn.edu.vnuk.shopping.service.user.AccountService;
import vn.edu.vnuk.shopping.service.user.CommonService;
import vn.edu.vnuk.shopping.view.View;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AdminAccountService adminAccountService;

    @Autowired
    private CommonService commonService;

    @JsonView(View.Public.class)
    @PostMapping(value = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Account account) throws EmailIsExitException, AccountValidationException {
        return new ResponseEntity<>(accountService.create(account), HttpStatus.OK);
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<Account>> getAll(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                                                @RequestParam(name = "accessToken") String accessToken,
                                                HttpServletRequest request,
                                                Pageable pageable) throws TokenNotFoundException, UnauthorizedException, TokenIsExpiredException, AccountNotFoundException {
        commonService.authenticate(accessToken, request);

        return new ResponseEntity<>(accountService.getAll(keyword, pageable), HttpStatus.OK);
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/api/accounts/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.getOne(id), HttpStatus.OK);
    }



    @JsonView(View.Public.class)
    @PutMapping(value = "/api/accounts/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id,
                                    @RequestParam(name = "accessToken") String accessToken,
                                    HttpServletRequest request,
                                    @RequestBody Account account) throws AccountNotFoundException, AccountValidationException, AccountPasswordIsIncorrectException, TokenNotFoundException, UnauthorizedException, TokenIsExpiredException {
        commonService.authenticate(accessToken, request);

        return new ResponseEntity<>(accountService.update(id, account), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/accounts/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id,
                                    @RequestParam(name = "accessToken") String accessToken,
                                    HttpServletRequest request) throws AccountNotFoundException, TokenNotFoundException, UnauthorizedException, TokenIsExpiredException {
        commonService.authenticate(accessToken,  request);

        adminAccountService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
