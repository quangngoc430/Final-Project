package vn.edu.vnuk.shopping.validation.Order;

import org.springframework.stereotype.Component;
import vn.edu.vnuk.shopping.exception.order.OrderValidationException;
import vn.edu.vnuk.shopping.model.Order;

import javax.validation.*;
import java.util.HashMap;
import java.util.Set;

@Component
public class OrderValidationImpl implements OrderValidation {

    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @Override
    public void validate(Order order, Class groupClassValidation) throws OrderValidationException {
        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order, groupClassValidation);

        HashMap<String, String> errors = new HashMap<>();

        if(!constraintViolations.isEmpty()){
            for(ConstraintViolation<Order> orderConstraintViolation : constraintViolations){
                errors.put(orderConstraintViolation.getPropertyPath().toString(), orderConstraintViolation.getMessage());
            }

            throw new OrderValidationException(constraintViolations.size(), errors);
        }
    }

}
