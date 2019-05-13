package vn.edu.vnuk.shopping.validation.comment;

import org.springframework.stereotype.Service;
import vn.edu.vnuk.shopping.exception.comment.CommentValidationException;
import vn.edu.vnuk.shopping.model.Comment;

import javax.validation.*;
import java.util.HashMap;
import java.util.Set;

@Service
public class CommentValidationImpl implements CommentValidation {

    private static final Validator validator;

    static {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @Override
    public void validate(Comment comment, Class classGroupValidation) throws CommentValidationException {
        Set<ConstraintViolation<Comment>> constraintViolations = validator.validate(comment, classGroupValidation);

        HashMap<String, String> errors = new HashMap<>();

        if(!constraintViolations.isEmpty()){
            for(ConstraintViolation<Comment> commentConstraintViolation : constraintViolations){
                errors.put(commentConstraintViolation.getPropertyPath().toString(), commentConstraintViolation.getMessage());
            }

            throw new CommentValidationException(constraintViolations.size(), errors);
        }
    }
}
