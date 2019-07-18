package vn.edu.vnuk.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.Ordering;

@Repository
public interface OrderRepository extends CrudRepository<Ordering, Long> {

    @Query("SELECT new vn.edu.vnuk.shopping.model.Ordering(ordering, orderAddress) " +
           "FROM Ordering ordering " +
           "INNER JOIN OrderAddress orderAddress " +
           "ON ordering.orderAddressId = orderAddress.id " +
           "WHERE ordering.accountId = :accountId")
    Page<Ordering> getAllByAccountId(@Param("accountId") Long accountId, Pageable pageable);

    @Query("SELECT new vn.edu.vnuk.shopping.model.Ordering(ordering, orderAddress) " +
           "FROM Ordering ordering " +
           "INNER JOIN OrderAddress orderAddress " +
           "ON ordering.orderAddressId = orderAddress.id")
    Page<Ordering> getAllBy(Pageable pageable);
}