package vn.edu.vnuk.shopping.exception.rating;

public class RatingIsExistException extends Exception{

    public RatingIsExistException(Long ratingId){
        super("RatingIsExistException with id = " + ratingId);
    }

}
