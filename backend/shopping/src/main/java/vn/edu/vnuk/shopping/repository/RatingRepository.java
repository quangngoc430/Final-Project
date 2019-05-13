package vn.edu.vnuk.shopping.repository;

import org.springframework.data.repository.CrudRepository;
import vn.edu.vnuk.shopping.model.Rating;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Long> {

    List<Rating> findAllByItemId(Long itemId);
}
