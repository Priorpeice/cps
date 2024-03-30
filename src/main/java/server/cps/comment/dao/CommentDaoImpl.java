package server.cps.comment.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import server.cps.comment.repository.CommentRepository;
import server.cps.entity.Comment;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {
    @Autowired
    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> searchComment(Long boardId) {
        return commentRepository.findCommentByBoard_Id(boardId);
    }

    @Override
    public void delete(Long id) {
        Comment comment=commentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
        commentRepository.delete(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
    }
}
