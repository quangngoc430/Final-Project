package vn.edu.vnuk.shopping.validation.item;

import org.springframework.stereotype.Component;
import vn.edu.vnuk.shopping.exception.account.AccountValidationException;
import vn.edu.vnuk.shopping.exception.item.ItemValidationException;
import vn.edu.vnuk.shopping.model.Item;
import vn.edu.vnuk.shopping.model.OauthAccessToken;

import javax.validation.*;
import java.util.HashMap;
import java.util.Set;

@Component
public class ItemValidationImpl implements ItemValidation {

    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @Override
    public void validate(Item item, Class groupClassValidation) throws ItemValidationException {
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item, groupClassValidation);

        HashMap<String, String> errors = new HashMap<>();

        if(!constraintViolations.isEmpty()){
            for(ConstraintViolation<Item> itemConstraintViolation : constraintViolations){
                errors.put(itemConstraintViolation.getPropertyPath().toString(), itemConstraintViolation.getMessage());
            }

            throw new ItemValidationException(constraintViolations.size(), errors);
        }
    }
}
