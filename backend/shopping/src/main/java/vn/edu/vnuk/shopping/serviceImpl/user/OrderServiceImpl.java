package vn.edu.vnuk.shopping.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.exception.order.OrderNotFoundException;
import vn.edu.vnuk.shopping.model.Ordering;
import vn.edu.vnuk.shopping.repository.OrderRepository;
import vn.edu.vnuk.shopping.service.user.AccountService;
import vn.edu.vnuk.shopping.service.user.OrderService;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public Ordering create(Ordering order) {

        order.setAccountId(accountService.getAccountLogin().getId());
        order.setStatus(1L);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());

        return orderRepository.save(order);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_NORMAL_USER')")
    public Page<Ordering> getAllByAccountId(Long accountId, Pageable pageable) {
        return orderRepository.getAllByAccountId(accountId, pageable);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_NORMAL_USER')")
    public Page<Ordering> getAll(Pageable pageable) {
        return orderRepository.getAllBy(pageable);
    }

    @Override
    public Ordering getOne(Long orderId) throws OrderNotFoundException {
        Optional<Ordering> optionalOrdering = orderRepository.findById(orderId);

        if (!optionalOrdering.isPresent()) throw new OrderNotFoundException(orderId);
        return orderRepository.getById(orderId);
    }
}
