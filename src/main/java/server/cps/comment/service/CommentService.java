package server.cps.comment.service;

import server.cps.comment.dto.CommentRequestDto;
import server.cps.entity.Board;
import server.cps.entity.Comment;
import server.cps.entity.Member;

import java.util.List;

public interface CommentService {
    Comment saveComment(CommentRequestDto commentRequestDto , Board board, Member member);
    List<Comment> searchComment(Long boardId);
    Comment showComment(Long id);
    void deleteComment(Long id);


}
