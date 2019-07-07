package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.common.UnauthorizedException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Rating;
import vn.edu.vnuk.shopping.service.user.CommonService;
import vn.edu.vnuk.shopping.service.user.RatingService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommonService commonService;

    @PostMapping(value = "/api/ratings", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Rating rating,
                                    @RequestParam(name = "accessToken") String accessToken,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException {

        commonService.authenticate(accessToken, request);

        return new ResponseEntity<>(ratingService.create(rating), HttpStatus.OK);
    }

    @GetMapping(value = "/api/ratings/{itemId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllByItemId(@PathVariable(name = "itemId") Long itemId) {
        return new ResponseEntity<>(ratingService.getAllByItemId(itemId), HttpStatus.OK);
    }

}
