package vn.edu.vnuk.shopping.exception.rating;

public class RatingNotFoundException extends Exception {

    public RatingNotFoundException(long ratingId) {
        super("RatingNotFoundException with id = " + ratingId);
    }

}
