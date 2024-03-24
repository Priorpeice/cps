package server.cps.respository.problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.cps.entity.Board;

import java.util.List;
@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Board save(Board board);
    List<Board> findAll();
    void deleteById(Long id);

}
