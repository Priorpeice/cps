package server.cps.board.service;

import server.cps.board.dto.BoardRequestDto;
import server.cps.board.dto.BoardSerachRequestDTO;
import server.cps.entity.Board;

import java.util.List;

public interface BoardService {
    Board saveBoard(BoardRequestDto boardRequestDto);
    Board findBoard(Long id);
    Board updateBoard(Long id, BoardRequestDto boardRequestDto);
    List<Board> showBoardAll();
    List<Board> searchBoards(BoardSerachRequestDTO boardSerachRequestDTO);
    void deleteBoard(Long id) ;

}
