package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.common.UnauthorizedException;
import vn.edu.vnuk.shopping.exception.rating.RatingIsExistException;
import vn.edu.vnuk.shopping.exception.rating.RatingNotFoundException;
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

    @GetMapping(value = "/api/items/{itemId}/ratings", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllByItemId(@PathVariable(name = "itemId") Long itemId) {
        return new ResponseEntity<>(ratingService.getAllByItemId(itemId), HttpStatus.OK);
    }

    @PostMapping(value = "/api/ratings", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Rating rating,
                                    @RequestParam(name = "accessToken") String accessToken,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException, RatingIsExistException {

        commonService.authenticate(accessToken, request);

        return new ResponseEntity<>(ratingService.create(rating, accessToken), HttpStatus.OK);
    }

    @PutMapping(value = "/api/ratings/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@PathVariable(name = "id") Long ratingId,
                                    @RequestBody Rating rating,
                                    @RequestParam(name = "accessToken") String accessToken,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException, RatingNotFoundException {
        commonService.authenticate(accessToken, request);

        return new ResponseEntity<>(ratingService.update(ratingId, rating, accessToken), HttpStatus.OK);
    }
}
