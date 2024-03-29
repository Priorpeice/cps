package server.cps.comment.dao;

import server.cps.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Comment save(Comment comment);

    List<Comment> searchComment(Long boardId);

    void delete(Long id);
    Optional<Comment> findById(Long id);
}
