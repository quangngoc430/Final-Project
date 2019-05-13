package vn.edu.vnuk.shopping.validation.token;

import org.springframework.stereotype.Component;
import vn.edu.vnuk.shopping.exception.account.AccountValidationException;
import vn.edu.vnuk.shopping.model.OauthAccessToken;

import javax.validation.*;
import java.util.HashMap;
import java.util.Set;

@Component
public class TokenValidationImpl implements TokenValidation {

    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @Override
    public void validate(OauthAccessToken oauthAccessToken, Class groupClassValidation) throws AccountValidationException {
        Set<ConstraintViolation<OauthAccessToken>> constraintViolations = validator.validate(oauthAccessToken, groupClassValidation);

        HashMap<String, String> errors = new HashMap<>();

        if(!constraintViolations.isEmpty()){
            for(ConstraintViolation<OauthAccessToken> commentConstraintViolation : constraintViolations){
                errors.put(commentConstraintViolation.getPropertyPath().toString(), commentConstraintViolation.getMessage());
            }

            throw new AccountValidationException(constraintViolations.size(), errors);
        }
    }
}
