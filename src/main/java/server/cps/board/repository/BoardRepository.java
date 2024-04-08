package server.cps.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.cps.entity.Board;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Board save(Board board);
    Optional<Board> findById(Long id);
    List<Board> findAll();
    void deleteById(Long id);
    List<Board> findByTitleContainingIgnoreCase(String title);

}
