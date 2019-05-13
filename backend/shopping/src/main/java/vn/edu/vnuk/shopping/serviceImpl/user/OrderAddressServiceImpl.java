package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.exception.account.AccountNotFoundException;
import vn.edu.vnuk.shopping.exception.orderAddress.OrderAddressNotFoundException;
import vn.edu.vnuk.shopping.exception.orderAddress.OrderAddressValidationException;
import vn.edu.vnuk.shopping.model.Account;
import vn.edu.vnuk.shopping.model.OrderAddress;
import vn.edu.vnuk.shopping.repository.AccountRepository;
import vn.edu.vnuk.shopping.repository.OrderAddressRepository;
import vn.edu.vnuk.shopping.service.user.AccountService;
import vn.edu.vnuk.shopping.service.user.OrderAddressService;
import vn.edu.vnuk.shopping.validation.OrderAddress.OrderAddressValidation;

import javax.validation.groups.Default;
import java.util.Date;
import java.util.Optional;

@Service
public class OrderAddressServiceImpl implements OrderAddressService {

    @Autowired
    private OrderAddressRepository orderAddressRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderAddressValidation orderAddressValidation;

    @Autowired
    private AccountService accountService;

    @Override
    public OrderAddress getOne(Long orderAddressId) throws OrderAddressNotFoundException {
        Optional<OrderAddress> orderAddressOptional = orderAddressRepository.findById(orderAddressId);

        if (!orderAddressOptional.isPresent()) throw new OrderAddressNotFoundException(orderAddressId);

        return orderAddressOptional.get();
    }

    @Override
    public Page<OrderAddress> getAll(Long accountId, Pageable pageable) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (!accountOptional.isPresent()) throw new AccountNotFoundException(accountId);

        return orderAddressRepository.findAllByAccontId(accountId, pageable);
    }

    @Override
    public OrderAddress create(OrderAddress orderAddress) throws OrderAddressValidationException {
        orderAddressValidation.validate(orderAddress, Default.class);

        Account accountLogin = accountService.getAccountLogin();

        orderAddress.setAccontId(accountLogin.getId());
        orderAddress.setCreatedAt(new Date());
        orderAddress.setUpdatedAt(new Date());

        return orderAddressRepository.save(orderAddress);
    }

    @Override
    public OrderAddress update(Long orderAddressId, OrderAddress orderAddress) throws OrderAddressValidationException, OrderAddressNotFoundException {
        orderAddressValidation.validate(orderAddress, Default.class);

        Optional<OrderAddress> orderAddressOptional = orderAddressRepository.findById(orderAddressId);

        if (!orderAddressOptional.isPresent()) throw new OrderAddressNotFoundException(orderAddressId);

        OrderAddress oldOrderAddress = orderAddressOptional.get();
        oldOrderAddress.setAddress(orderAddress.getAddress());
        oldOrderAddress.setCity(orderAddress.getCity());
        oldOrderAddress.setDistrict(orderAddress.getDistrict());
        oldOrderAddress.setFullname(orderAddress.getFullname());
        oldOrderAddress.setPhone(orderAddress.getPhone());

        return orderAddressRepository.save(oldOrderAddress);
    }

    @Override
    public void delete(Long orderAddressId) throws OrderAddressNotFoundException {
        Optional<OrderAddress> orderAddressOptional = orderAddressRepository.findById(orderAddressId);

        if (!orderAddressOptional.isPresent()) throw new OrderAddressNotFoundException(orderAddressId);

        orderAddressRepository.deleteById(orderAddressId);
    }
}
