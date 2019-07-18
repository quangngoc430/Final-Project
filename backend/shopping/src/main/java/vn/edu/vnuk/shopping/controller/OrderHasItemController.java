package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.common.UnauthorizedException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.OrderHasItem;
import vn.edu.vnuk.shopping.service.user.CommonService;
import vn.edu.vnuk.shopping.service.user.OrderHasItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OrderHasItemController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private OrderHasItemService orderHasItemService;

    @PostMapping(value = "/api/order-has-item", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody List<OrderHasItem> orderHasItemList,
                                    @RequestParam(value = "accessToken") String accessToken,
                                    HttpServletRequest request) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException {
        commonService.authenticate(accessToken, request);

        orderHasItemService.create(orderHasItemList);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/order-has-item", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll(@RequestParam("orderId") Long orderId,
                                    @RequestParam(value = "accessToken") String accessToken,
                                    HttpServletRequest request,
                                    Pageable pageable) throws TokenIsExpiredException, AccountNotFoundException, TokenNotFoundException, UnauthorizedException {
        commonService.authenticate(accessToken, request);

        if (orderId == null) {
            return new ResponseEntity<>(orderHasItemService.getAllBy(pageable), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orderHasItemService.getAllByOrderId(orderId, pageable), HttpStatus.OK);
        }
    }
}
