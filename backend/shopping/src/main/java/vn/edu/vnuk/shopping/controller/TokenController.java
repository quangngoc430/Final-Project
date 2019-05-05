package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.service.user.TokenService;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping(value = "/api/token", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> checkToken(@RequestParam(name = "accessToken", required = true) String accessToken) throws TokenNotFoundException, TokenIsExpiredException {
        return new ResponseEntity<>(tokenService.get(accessToken), HttpStatus.OK);
    }

}
