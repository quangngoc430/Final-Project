package vn.edu.vnuk.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.OrderAddress;

import java.util.List;

@Repository
public interface OrderAddressRepository extends CrudRepository<OrderAddress, Long> {

    Page<OrderAddress> findAllBy(Pageable pageable);

    List<OrderAddress> findAllByAccountId(Long AccountId);
}