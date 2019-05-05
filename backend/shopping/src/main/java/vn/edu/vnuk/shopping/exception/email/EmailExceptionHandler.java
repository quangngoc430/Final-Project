package vn.edu.vnuk.shopping.exception.email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.vnuk.shopping.exception.email.EmailAndPasswordIsIncorrectException;
import vn.edu.vnuk.shopping.exception.email.EmailIsExitException;
import vn.edu.vnuk.shopping.model.ApiError;
import vn.edu.vnuk.shopping.exception.email.EmailFormatException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class EmailExceptionHandler {

    private static final String MESSAGE = "message";

    @ExceptionHandler(value = EmailFormatException.class)
    public ResponseEntity<ApiError> handleEmailFormatException(HttpServletRequest request, Exception ex){

        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "Wrong email format");

        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = EmailIsExitException.class)
    public ResponseEntity<ApiError> handleEmailIsExitException(HttpServletRequest request, Exception ex) {

        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "Email is exist");

        ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EmailAndPasswordIsIncorrectException.class)
    public ResponseEntity<ApiError> handleEmailAndPasswordIsIncorrectException(HttpServletRequest request, Exception ex) {

        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "Email and password is correct");

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
