package vn.edu.vnuk.shopping.exception.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.vnuk.shopping.model.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class ItemExceptionHandler {

    @ExceptionHandler(value = ItemNotFoundException.class)
    public ResponseEntity<ApiError> handleItemNotFoundException(HttpServletRequest request, Exception ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "item not found");

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
