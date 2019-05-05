package vn.edu.vnuk.shopping.exception.comment;

public class CommentNotFoundException extends Exception {

    public CommentNotFoundException(Long commentId) {
        super("CommentNotFoundException with commentId = " + commentId);
    }
}
