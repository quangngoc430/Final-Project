package vn.edu.vnuk.shopping.service.user;

import java.util.List;

import vn.edu.vnuk.shopping.model.Comment;

public interface CommentService {

    List<Comment> getAllByItemId(Long itemId);

    Comment getById(Long commentId);
    
    Comment create(Comment comment);

    Comment update(Long commentId, Comment comment);

    void deleteById(Long commentId);

}