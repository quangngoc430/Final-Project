package vn.edu.vnuk.shopping.exception.comment;

import java.util.Map;

public class CommentValidationException extends Exception {

    private Map<String, String> errors;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public CommentValidationException(long numberOfErrors, Map<String, String> errors){
        super("CommentValidationException with number of errors = " + numberOfErrors);
        this.errors = errors;
    }
}
