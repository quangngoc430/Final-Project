package vn.edu.vnuk.shopping.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.vnuk.shopping.model.Rating;

import java.util.List;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

    @Query("SELECT new vn.edu.vnuk.shopping.model.Rating(rating, account) " +
           "FROM Rating rating " +
           "INNER JOIN Account account " +
           "ON rating.accountId = account.id " +
           "WHERE rating.itemId = :itemId")
    List<Rating> findAllByItemId(@Param("itemId") Long itemId);

    Rating findByAccountIdAndItemId(Long accountId, Long itemId);

    List<Rating> findAllByAccountId(Long accountId);
}
