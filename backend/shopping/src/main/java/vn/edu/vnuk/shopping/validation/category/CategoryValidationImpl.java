package vn.edu.vnuk.shopping.validation.category;

import org.springframework.stereotype.Component;
import vn.edu.vnuk.shopping.exception.category.CategoryValidationException;
import vn.edu.vnuk.shopping.model.Category;

import javax.validation.*;
import java.util.HashMap;
import java.util.Set;

@Component
public class CategoryValidationImpl implements CategoryValidation {

    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @Override
    public void validate(Category category, Class groupClassValidation) throws CategoryValidationException {
        Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category, groupClassValidation);

        HashMap<String, String> errors = new HashMap<>();

        if(!constraintViolations.isEmpty()){
            for(ConstraintViolation<Category> commentConstraintViolation : constraintViolations){
                errors.put(commentConstraintViolation.getPropertyPath().toString(), commentConstraintViolation.getMessage());
            }

            throw new CategoryValidationException(constraintViolations.size(), errors);
        }
    }
}
