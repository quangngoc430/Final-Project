package vn.edu.vnuk.shopping.validation.comment;

import vn.edu.vnuk.shopping.exception.comment.CommentValidationException;
import vn.edu.vnuk.shopping.model.Comment;

public interface CommentValidation {

    void validate(Comment comment, Class groupClassValidation) throws CommentValidationException;

}
