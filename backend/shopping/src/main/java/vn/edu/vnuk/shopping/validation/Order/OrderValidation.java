package vn.edu.vnuk.shopping.validation.Order;

import vn.edu.vnuk.shopping.exception.order.OrderValidationException;
import vn.edu.vnuk.shopping.exception.orderAddress.OrderAddressValidationException;
import vn.edu.vnuk.shopping.model.Order;
import vn.edu.vnuk.shopping.model.OrderAddress;

public interface OrderValidation {
    void validate(Order order, Class groupClassValidation) throws OrderValidationException;
}
