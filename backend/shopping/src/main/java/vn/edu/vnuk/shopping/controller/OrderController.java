package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.common.UnauthorizedException;
import vn.edu.vnuk.shopping.exception.order.OrderNotFoundException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Ordering;
import vn.edu.vnuk.shopping.service.user.CommonService;
import vn.edu.vnuk.shopping.service.user.OrderService;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/api/orders", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Ordering order,
                                    @RequestParam("accessToken") String accessToken,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException {
        commonService.authenticate(accessToken, request);

        return new ResponseEntity<>(orderService.create(order), HttpStatus.OK);
    }

    @GetMapping(value = "/api/orders/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long orderId,
                                    @RequestParam("accessToken") String accessToken,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException, OrderNotFoundException {
        commonService.authenticate(accessToken, request);

        return new ResponseEntity<>(orderService.getOne(orderId), HttpStatus.OK);
    }

    @GetMapping(value = "/api/orders", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll(@RequestParam(name = "accountId", required = false) Long accountId,
                                    @RequestParam("accessToken") String accessToken,
                                    Pageable pageable,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException {
        commonService.authenticate(accessToken, request);

        if (accountId != null)
            return new ResponseEntity<>(orderService.getAllByAccountId(accountId, pageable), HttpStatus.OK);
        return new ResponseEntity<>(orderService.getAll(pageable), HttpStatus.OK);
    }

}
