package vn.edu.vnuk.shopping.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.service.user.AccountService;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Account account) {
        accountService.create(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/accounts/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(accountService.getOne(id), HttpStatus.OK);
    }

    @GetMapping(value = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll(String keyword, Pageable pageable) {
        return null;
    }

}
