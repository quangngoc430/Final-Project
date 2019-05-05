package vn.edu.vnuk.shopping.service.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.vnuk.shopping.exception.comment.CommentNotFoundException;
import vn.edu.vnuk.shopping.exception.comment.CommentValidationException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.model.Comment;

public interface CommentService {

    Page<Comment> getAllByItemId(Long itemId, Pageable pageable) throws ItemNotFoundException;

    Comment getOne(Long commentId) throws CommentNotFoundException;
    
    Comment create(Comment comment) throws CommentValidationException;

    Comment update(Long commentId, Comment comment) throws CommentNotFoundException, CommentValidationException;

    void delete(Long commentId) throws CommentNotFoundException;

}