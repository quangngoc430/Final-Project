package vn.edu.vnuk.shopping.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.vnuk.shopping.model.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class AccountExceptionHandler {

    private static final String MESSAGE = "message";

    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<ApiError> handleAccountNotFoundException(HttpServletRequest request, Exception ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "account not found");

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccountIsExistException.class)
    public ResponseEntity<ApiError> handleAccountIsExistException(HttpServletRequest request, Exception ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "account is exist");

        ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = AccountValidationException.class)
    public ResponseEntity<ApiError> handleAccountValidationException(HttpServletRequest request, Exception ex){
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE.value(),
                                        ((AccountValidationException) ex).getErrors(),
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = AccountPasswordIsIncorrectException.class)
    public ResponseEntity<ApiError> handleAccountPasswordNotEqualException(HttpServletRequest request, Exception ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "Two passwords do not match");

        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = AccountInvalidException.class)
    public ResponseEntity<ApiError> handleAccountInvalidException(HttpServletRequest request, Exception ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "Invalid email or passowrd");

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccountInactiveException.class)
    public ResponseEntity<ApiError> handleAccountInactiveException(HttpServletRequest request, Exception ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "account is inactive");

        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
}

    @ExceptionHandler(value = AccountIsLockedException.class)
    public ResponseEntity<ApiError> handleAccountLockedException(HttpServletRequest request, Exception ex){
        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "account is locked");

        ApiError apiError = new ApiError(HttpStatus.LOCKED.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.LOCKED);
    }

}
