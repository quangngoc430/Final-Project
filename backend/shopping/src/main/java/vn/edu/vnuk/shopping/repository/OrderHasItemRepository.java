package vn.edu.vnuk.shopping.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.OrderHasItem;

@Repository
public interface OrderHasItemRepository extends CrudRepository<OrderHasItem, Long> {
}