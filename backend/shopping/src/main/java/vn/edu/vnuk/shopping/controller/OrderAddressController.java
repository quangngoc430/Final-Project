package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnuk.shopping.service.user.OrderAddressService;

@RestController
public class OrderAddressController {

    @Autowired
    private OrderAddressService orderAddressService;
}
