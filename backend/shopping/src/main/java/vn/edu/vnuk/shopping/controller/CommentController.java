package vn.edu.vnuk.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnuk.shopping.exception.comment.CommentNotFoundException;
import vn.edu.vnuk.shopping.exception.comment.CommentValidationException;
import vn.edu.vnuk.shopping.exception.item.ItemNotFoundException;
import vn.edu.vnuk.shopping.model.Comment;
import vn.edu.vnuk.shopping.service.user.CommentService;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/api/items/{itemId}/comments", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<Comment>> getAll(@PathVariable(name = "itemId") Long itemId,
                                                Pageable pageable) throws ItemNotFoundException {
        return new ResponseEntity<>(commentService.getAllByItemId(itemId, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/api/comments/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Comment> getOne(@PathVariable(name = "id") Long itemId) throws CommentNotFoundException {
        return new ResponseEntity<>(commentService.getOne(itemId), HttpStatus.OK);
    }

    @PostMapping(value = "/api/comments", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Comment> create(@RequestBody Comment comment) throws CommentValidationException {
        return new ResponseEntity<>(commentService.create(comment), HttpStatus.OK);
    }

    @PutMapping(value = "/api/comments/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Comment> update(@PathVariable(name = "id") Long id,
                                          @RequestBody Comment comment) throws CommentNotFoundException, CommentValidationException {
        return new ResponseEntity<>(commentService.update(id, comment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/comments/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) throws CommentNotFoundException {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
