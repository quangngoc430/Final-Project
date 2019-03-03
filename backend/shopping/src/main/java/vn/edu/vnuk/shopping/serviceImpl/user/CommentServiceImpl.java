package vn.edu.vnuk.shopping.serviceImpl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.vnuk.shopping.model.Comment;
import vn.edu.vnuk.shopping.repository.CommentRepository;
import vn.edu.vnuk.shopping.service.user.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getAllByItemId(Long itemId) {
        // TODO: check itemId is exists
        return commentRepository.findAllByItemId(itemId);
    }

    @Override
    public Comment getById(Long commentId) {
        // TODO: check commentId is exists
        return commentRepository.findById(commentId).get();
    }

    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long commentId, Comment comment) {
        // TODO: check commentId is exists
        Comment oldComment = commentRepository.findById(commentId).get();

        oldComment.setContent(comment.getContent());

        return oldComment;
    }

    @Override
    public void deleteById(Long commentId) {
        // TODO: check commentId is exists
        commentRepository.deleteById(commentId);
    }

}