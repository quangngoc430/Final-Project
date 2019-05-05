package vn.edu.vnuk.shopping.validation;

import vn.edu.vnuk.shopping.exception.category.CategoryValidationException;
import vn.edu.vnuk.shopping.model.Category;

public interface CategoryValidation {

    void validate(Category category, Class classGroupValidation) throws CategoryValidationException;
}
