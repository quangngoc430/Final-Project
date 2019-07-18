package vn.edu.vnuk.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.OrderHasItem;

@Repository
public interface OrderHasItemRepository extends CrudRepository<OrderHasItem, Long> {

    @Query("SELECT new vn.edu.vnuk.shopping.model.OrderHasItem(orderHasItem, item) " +
           "FROM OrderHasItem orderHasItem " +
           "INNER JOIN Item item " +
           "ON orderHasItem.itemId = item.id " +
           "where orderHasItem.orderId = :orderId")
    Page<OrderHasItem> getAllByOrderId(@Param("orderId") Long orderId, Pageable pageable);

    @Query("SELECT new vn.edu.vnuk.shopping.model.OrderHasItem(orderHasItem, item) " +
            "FROM OrderHasItem orderHasItem " +
            "INNER JOIN Item item " +
            "ON orderHasItem.itemId = item.id")
    Page<OrderHasItem> getAllBy(Pageable pageable);
}