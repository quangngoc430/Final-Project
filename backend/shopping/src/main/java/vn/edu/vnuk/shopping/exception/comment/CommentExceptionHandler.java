package vn.edu.vnuk.shopping.exception.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.vnuk.shopping.model.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class CommentExceptionHandler {

    private static final String MESSAGE = "message";

    @ExceptionHandler(value = CommentNotFoundException.class)
    public ResponseEntity<ApiError> handleCommentNotFoundException(HttpServletRequest request, Exception ex) {
        HashMap<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "comment not found");

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),
                                        errors,
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CommentValidationException.class)
    public ResponseEntity<ApiError> handleCommentValidationException(HttpServletRequest request, Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE.value(),
                                        ((CommentValidationException) ex).getErrors(),
                                        ex.getMessage(),
                                        request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }
}
