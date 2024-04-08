package server.cps.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.cps.board.dao.BoardDao;
import server.cps.comment.dao.CommentDao;
import server.cps.comment.dto.CommentRequestDto;
import server.cps.entity.Board;
import server.cps.entity.Comment;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final BoardDao boardDao;

    public Comment saveComment (CommentRequestDto commentRequestDto , Board board){
        Comment comment=commentRequestDto.toEntity(board);
        return commentDao.save(comment);
    }
    public List<Comment> searchComment(Long boardId) {
        return commentDao.searchComment(boardId);
    }
    public void deleteComment(Long id ) {
        commentDao.delete(id);
    }

    public Comment showComment(Long id)
    {
        return commentDao.findById(id);
    }

}
