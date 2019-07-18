package vn.edu.vnuk.shopping.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.vnuk.shopping.model.Ordering;

public interface OrderService {

    Ordering create(Ordering order);

    Page<Ordering> getAllByAccountId(Long accountId, Pageable pageable);

    Page<Ordering> getAll(Pageable pageable);
}
