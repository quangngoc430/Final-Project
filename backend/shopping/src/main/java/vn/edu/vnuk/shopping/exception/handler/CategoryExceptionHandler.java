package vn.edu.vnuk.shopping.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.vnuk.shopping.exception.CategoryIsExistException;
import vn.edu.vnuk.shopping.exception.CategoryNotFoundException;
import vn.edu.vnuk.shopping.exception.CategoryValidationException;
import vn.edu.vnuk.shopping.model.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;

@ControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFoundException(HttpServletRequest request, Exception ex){
        ApiError apiError = new ApiError();

        apiError.setTimestamp(Instant.now());
        apiError.setStatus(HttpStatus.NOT_FOUND.value());
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "Category not found");
        apiError.setError(errors);
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryIsExistException.class)
    public ResponseEntity<ApiError> handleCategoryIsExistException(HttpServletRequest request, Exception ex){
        ApiError apiError = new ApiError();

        apiError.setTimestamp(Instant.now());
        apiError.setStatus(HttpStatus.CONFLICT.value());
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "Category is exist");
        apiError.setError(errors);
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = CategoryValidationException.class)
    public ResponseEntity<ApiError> handleCategoryValidationException(HttpServletRequest request, Exception ex){
        ApiError apiError = new ApiError();

        apiError.setTimestamp(Instant.now());
        apiError.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        apiError.setError(((CategoryValidationException) ex).getErrors());
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

}