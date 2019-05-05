package vn.edu.vnuk.shopping.serviceImpl.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.edu.vnuk.shopping.exception.comment.CommentNotFoundException;
import vn.edu.vnuk.shopping.exception.comment.CommentValidationException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.model.Comment;
import vn.edu.vnuk.shopping.repository.CommentRepository;
import vn.edu.vnuk.shopping.repository.ItemRepository;
import vn.edu.vnuk.shopping.service.user.CommentService;
import vn.edu.vnuk.shopping.validation.CommentValidation;

import javax.validation.groups.Default;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CommentValidation commentValidation;

    @Override
    public Page<Comment> getAllByItemId(Long itemId, Pageable pageable) throws ItemNotFoundException {
        if (!itemRepository.existsById(itemId)) throw new ItemNotFoundException(itemId);

        return commentRepository.findAllByItemId(itemId, pageable);
    }

    @Override
    public Comment getOne(Long commentId) throws CommentNotFoundException {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (!commentOptional.isPresent()) throw new CommentNotFoundException(commentId);

        return commentOptional.get();
    }

    @Override
    public Comment create(Comment comment) throws CommentValidationException {
        commentValidation.validate(comment, Default.class);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long commentId, Comment comment) throws CommentNotFoundException, CommentValidationException {
        commentValidation.validate(comment, Default.class);

        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (!commentOptional.isPresent()) throw new CommentNotFoundException(commentId);

        Comment oldComment = commentOptional.get();

        oldComment.setContent(comment.getContent());

        return oldComment;
    }

    @Override
    public void delete(Long commentId) throws CommentNotFoundException {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (!commentOptional.isPresent()) throw new CommentNotFoundException(commentId);

        commentRepository.deleteById(commentId);
    }

}