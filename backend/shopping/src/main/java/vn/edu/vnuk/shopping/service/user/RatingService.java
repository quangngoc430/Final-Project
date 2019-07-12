package vn.edu.vnuk.shopping.service.user;

import vn.edu.vnuk.shopping.exception.rating.RatingIsExistException;
import vn.edu.vnuk.shopping.exception.rating.RatingNotFoundException;
import vn.edu.vnuk.shopping.exception.token.TokenIsExpiredException;
import vn.edu.vnuk.shopping.exception.token.TokenNotFoundException;
import vn.edu.vnuk.shopping.model.Rating;

import java.util.List;

public interface RatingService {

    Rating create(Rating rating, String accessToken) throws TokenIsExpiredException, TokenNotFoundException, RatingIsExistException;

    Rating update(Long ratingId, Rating rating, String accessToken) throws RatingNotFoundException;

    List<Rating> getAllByItemId(Long itemId);
}
