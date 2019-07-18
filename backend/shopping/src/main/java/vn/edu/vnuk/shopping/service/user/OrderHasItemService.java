package vn.edu.vnuk.shopping.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.vnuk.shopping.model.OrderHasItem;

import java.util.List;

public interface OrderHasItemService {

    void create(List<OrderHasItem> orderHasItemList);

    Page<OrderHasItem> getAllByOrderId(Long orderId, Pageable pageable);

    Page<OrderHasItem> getAllBy(Pageable pageable);
}
