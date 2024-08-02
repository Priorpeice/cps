package server.cps.board.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.cps.entity.Board;

public interface BoardDao {
    Board save(Board board);
    Board findById(Long id);
    void deleteById(Long id);
    Page<Board> search(Pageable pageable,String title);
    Page<Board> findAll(Pageable pageable);


}
