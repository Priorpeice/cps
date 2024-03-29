package server.cps.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.cps.entity.Comment;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment save(Comment comment);

    List<Comment> findCommentByBoard_Id(Long boardId);

    void deleteById(Long id);
    Optional<Comment> findById(Long id);
}
