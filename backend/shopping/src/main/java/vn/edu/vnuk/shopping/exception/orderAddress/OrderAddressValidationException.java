package vn.edu.vnuk.shopping.exception.orderAddress;

import java.util.Map;

public class OrderAddressValidationException extends Exception {

    private Map<String, String> errors;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public OrderAddressValidationException(long numberOfErrors, Map<String, String> errors){
        super("OrderAddressValidationException with number of errors = " + numberOfErrors);
        this.errors = errors;
    }

}
