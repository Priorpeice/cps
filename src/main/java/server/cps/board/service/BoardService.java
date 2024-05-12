package server.cps.board.service;

import org.springframework.data.domain.Pageable;
import server.cps.board.dto.BoardDto;
import server.cps.board.dto.BoardRequestDto;
import server.cps.board.dto.BoardResponseDto;
import server.cps.board.dto.BoardSerachRequestDTO;
import server.cps.entity.Board;
import server.cps.entity.Member;

import java.util.List;

public interface BoardService {
    BoardResponseDto saveBoard(BoardRequestDto boardRequestDto, Member member);
    Board findBoard(Long id);
    Board updateBoard(Long id, BoardRequestDto boardRequestDto);
    List<BoardDto> showBoardAll();
    List<BoardDto> searchBoards(Pageable pageable,BoardSerachRequestDTO boardSerachRequestDTO);
    void deleteBoard(Long id) ;
    List<BoardDto> findAllBoards(Pageable pageable);

}
