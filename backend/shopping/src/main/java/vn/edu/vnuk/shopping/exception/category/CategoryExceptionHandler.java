package vn.edu.vnuk.shopping.exception.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.vnuk.shopping.model.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class CategoryExceptionHandler {



    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFoundException(HttpServletRequest request, Exception ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "category not found");

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryIsExistException.class)
    public ResponseEntity<ApiError> handleCategoryIsExistException(HttpServletRequest request, Exception ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "category is exist");

        ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = CategoryValidationException.class)
    public ResponseEntity<ApiError> handleCategoryValidationException(HttpServletRequest request, Exception ex){

        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE.value(),
                                        ((CategoryValidationException) ex).getErrors(),
                                        request.getRequestURI(),
                                        ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

}
