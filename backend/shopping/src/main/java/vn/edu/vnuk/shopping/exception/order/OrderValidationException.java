package vn.edu.vnuk.shopping.exception.order;

import java.util.Map;

public class OrderValidationException extends Exception {

    private Map<String, String> errors;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public OrderValidationException(long numberOfErrors, Map<String, String> errors){
        super("OrderValidationException with number of errors = " + numberOfErrors);
        this.errors = errors;
    }
}
