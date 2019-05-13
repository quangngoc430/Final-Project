package vn.edu.vnuk.shopping.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.orderAddress.OrderAddressNotFoundException;
import vn.edu.vnuk.shopping.exception.orderAddress.OrderAddressValidationException;
import vn.edu.vnuk.shopping.model.OrderAddress;

public interface OrderAddressService {

    OrderAddress getOne(Long orderAddressId) throws OrderAddressNotFoundException;

    Page<OrderAddress> getAll(Long accountId, Pageable pageable) throws AccountNotFoundException;

    OrderAddress create(OrderAddress orderAddress) throws OrderAddressValidationException;

    OrderAddress update(Long orderAddressId, OrderAddress orderAddress) throws OrderAddressValidationException, OrderAddressNotFoundException;

    void delete(Long orderAddressId) throws OrderAddressNotFoundException;

}
