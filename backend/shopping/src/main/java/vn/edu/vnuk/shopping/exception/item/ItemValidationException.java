package vn.edu.vnuk.shopping.exception.item;

import java.util.Map;

public class ItemValidationException extends Exception {

    private Map<String, String> errors;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public ItemValidationException(long numberOfErrors, Map<String, String> errors){
        super("ItemValidationException with number of errors = " + numberOfErrors);
        this.errors = errors;
    }

}
