package vn.edu.vnuk.shopping.validation.OrderAddress;

import org.springframework.stereotype.Component;
import vn.edu.vnuk.shopping.exception.orderAddress.OrderAddressValidationException;
import vn.edu.vnuk.shopping.model.OrderAddress;

import javax.validation.*;
import java.util.HashMap;
import java.util.Set;

@Component
public class OrderAddressValidationImpl implements OrderAddressValidation {

    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @Override
    public void validate(OrderAddress orderAddress, Class groupClassValidation) throws OrderAddressValidationException {
        Set<ConstraintViolation<OrderAddress>> constraintViolations = validator.validate(orderAddress, groupClassValidation);

        HashMap<String, String> errors = new HashMap<>();

        if(!constraintViolations.isEmpty()){
            for(ConstraintViolation<OrderAddress> orderAddressConstraintViolation : constraintViolations){
                errors.put(orderAddressConstraintViolation.getPropertyPath().toString(), orderAddressConstraintViolation.getMessage());
            }

            throw new OrderAddressValidationException(constraintViolations.size(), errors);
        }
    }

}
