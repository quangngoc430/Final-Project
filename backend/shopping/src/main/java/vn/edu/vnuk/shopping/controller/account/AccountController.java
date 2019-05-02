package vn.edu.vnuk.shopping.controller.account;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.AccountPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.AccountValidationException;
import vn.edu.vnuk.shopping.exception.EmailIsExitException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.service.user.AccountService;
import vn.edu.vnuk.shopping.view.View;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @JsonView(View.Public.class)
    @PostMapping(value = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Account account) throws EmailIsExitException, AccountValidationException {
        return new ResponseEntity<>(accountService.create(account), HttpStatus.OK);
    }

    @JsonView(View.Public.class)
    @GetMapping(value = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<Account>> getAll(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                                                Pageable pageable) {
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
                                    @RequestBody Account account) throws AccountNotFoundException, AccountValidationException, AccountPasswordIsIncorrectException {
        return new ResponseEntity<>(accountService.update(id, account), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/accounts/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) throws AccountNotFoundException {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
