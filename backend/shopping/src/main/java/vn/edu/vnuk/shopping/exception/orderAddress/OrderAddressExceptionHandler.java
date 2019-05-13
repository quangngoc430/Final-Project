package vn.edu.vnuk.shopping.exception.orderAddress;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.vnuk.shopping.model.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class OrderAddressExceptionHandler {

    @ExceptionHandler(value = OrderAddressNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderAddressNotFoundException(HttpServletRequest request, Exception ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "orderAddress not found");

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrderAddressValidationException.class)
    public ResponseEntity<ApiError> handleOrderValidationException(HttpServletRequest request, Exception ex){
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE.value(),
                                        ((OrderAddressValidationException) ex).getErrors(),
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

}
