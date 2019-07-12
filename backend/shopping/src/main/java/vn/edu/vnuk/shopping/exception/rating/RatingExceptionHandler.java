package vn.edu.vnuk.shopping.exception.rating;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.vnuk.shopping.model.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class RatingExceptionHandler {

    private static final String MESSAGE = "message";

    @ExceptionHandler(value = RatingNotFoundException.class)
    public ResponseEntity<ApiError> handleRatingNotFoundException(HttpServletRequest request, Exception ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "rating not found");

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),
                errors,
                ex.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RatingIsExistException.class)
    public ResponseEntity<ApiError> handleAccountIsExistException(HttpServletRequest request, Exception ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "rating is exist");

        ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(),
                errors,
                ex.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

}