package vn.edu.vnuk.shopping.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.AccountIsLockedException;
import vn.edu.vnuk.shopping.exception.AccountValidationException;
import vn.edu.vnuk.shopping.exception.EmailAndPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.service.user.TokenService;

@RestController
public class CommonController {

    @Autowired
    private TokenService tokenService;

    @PostMapping(value =  "/api/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@RequestBody Account account) throws EmailAndPasswordIsIncorrectException, AccountValidationException, AccountIsLockedException {

        return new ResponseEntity<>(tokenService.create(account), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> logout(@RequestParam("accessToken") String accessToken) throws TokenNotFoundException {
        tokenService.delete(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}