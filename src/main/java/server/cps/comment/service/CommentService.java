package server.cps.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.cps.board.dao.BoardDao;
import server.cps.comment.dao.CommentDao;
import server.cps.comment.dto.CommentRequestDto;
import server.cps.entity.Board;
import server.cps.entity.Comment;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentDao;
    private final BoardDao boardDao;

    public Comment save (CommentRequestDto commentRequestDto , Board board){
        Comment comment=commentRequestDto.toEntity(board);
        return commentDao.save(comment);
    }
    public List<Comment> searchComment(Long boardId) {
        return commentDao.searchComment(boardId);
    }
    public void delete(Long id ) {
        commentDao.delete(id);
    }

    public Optional<Comment> findById(Long id) {
        return commentDao.findById(id);
    }
}
