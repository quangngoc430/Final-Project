package vn.edu.vnuk.shopping.service.user;

import vn.edu.vnuk.shopping.model.Rating;

import java.util.List;

public interface RatingService {

    Rating create(Rating rating);

    List<Rating> getAllByItemId(Long itemId);
}
