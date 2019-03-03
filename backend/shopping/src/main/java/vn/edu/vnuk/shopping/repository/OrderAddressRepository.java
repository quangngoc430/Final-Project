package vn.edu.vnuk.shopping.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.OrderAddress;

@Repository
public interface OrderAddressRepository extends CrudRepository<OrderAddress, Long> {
}