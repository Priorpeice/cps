package server.cps.comment.service;

import server.cps.comment.dto.CommentRequestDto;
import server.cps.entity.Board;
import server.cps.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment saveComment(CommentRequestDto commentRequestDto , Board board);
    public List<Comment> searchComment(Long boardId);
    Optional<Comment> showComment(Long id);
    void deleteComment(Long id);

}
