package vn.edu.vnuk.shopping.validation.OrderAddress;

import vn.edu.vnuk.shopping.exception.orderAddress.OrderAddressValidationException;
import vn.edu.vnuk.shopping.model.OrderAddress;

public interface OrderAddressValidation {
    void validate(OrderAddress orderAddress, Class groupClassValidation) throws OrderAddressValidationException;
}
