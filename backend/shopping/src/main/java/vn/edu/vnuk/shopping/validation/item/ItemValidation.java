package vn.edu.vnuk.shopping.validation.item;

import vn.edu.vnuk.shopping.exception.item.ItemValidationException;
import vn.edu.vnuk.shopping.model.Item;

public interface ItemValidation {
    void validate(Item item, Class groupClassValidation) throws ItemValidationException;
}
