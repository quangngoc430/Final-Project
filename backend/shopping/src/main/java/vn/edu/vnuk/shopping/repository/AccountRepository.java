package vn.edu.vnuk.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.vnuk.shopping.model.Account;

@Repository
public interface AccountRepository  extends CrudRepository<Account, Long> {

    void deleteByEmail(String email);

    Account getByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select account " +
           "FROM Account account " +
           "WHERE lower(account.email) LIKE concat('%', lower(trim(:keyword)), '%') " +
           "OR lower(account.fullname) LIKE concat('%', lower(trim(:keyword)), '%')")
    Page<Account> getAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}

