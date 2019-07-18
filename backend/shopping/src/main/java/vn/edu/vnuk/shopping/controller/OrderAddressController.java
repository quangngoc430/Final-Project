package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.common.UnauthorizedException;
import vn.edu.vnuk.shopping.exception.orderAddress.OrderAddressValidationException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.OrderAddress;
import vn.edu.vnuk.shopping.service.user.CommonService;
import vn.edu.vnuk.shopping.service.user.OrderAddressService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderAddressController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private OrderAddressService orderAddressService;

    @PostMapping(value = "/api/order-addresses", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody OrderAddress orderAddress,
                                    @RequestParam(name = "accessToken") String accessToken,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException, OrderAddressValidationException {
        commonService.authenticate(accessToken, request);

        return new ResponseEntity<>(orderAddressService.create(orderAddress), HttpStatus.OK);
    }

    @GetMapping(value = "/api/order-addresses", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll(@RequestParam(name = "accessToken") String accessToken,
                                    @RequestParam(name = "accountId") Long accountId,
                                    Pageable pageable,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException {
        commonService.authenticate(accessToken, request);

        if (accountId == null) {
            return new ResponseEntity<>(orderAddressService.getAll(pageable), HttpStatus.OK);
        }

        return new ResponseEntity<>(orderAddressService.getAll(accountId), HttpStatus.OK);
    }
}
