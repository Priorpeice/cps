package server.cps.board.dao;

import server.cps.board.dto.BoardDto;
import server.cps.entity.Board;

import java.util.List;

public interface BoardDao {
    Board save(Board board);
    Board findById(Long id);
    List<BoardDto> findAllBoards();
    void deleteById(Long id);
    List<Board> search(String title);


}
