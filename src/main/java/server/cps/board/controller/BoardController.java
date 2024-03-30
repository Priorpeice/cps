package server.cps.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.cps.board.dto.BoardRequestDto;
import server.cps.board.dto.BoardSerachRequestDTO;
import server.cps.board.service.BoardService;
import server.cps.common.CpsResponse;
import server.cps.common.ResoponseBody;
import server.cps.common.Status;
import server.cps.entity.Board;

import java.util.List;

@RestController
@CrossOrigin
//@RequestMapping()
public class BoardController {
    @Autowired
    BoardService boardService;
    @GetMapping("/api/boards")
    public List<Board> getAllBoards() {
        return boardService.showBoardAll();
    }
    @PostMapping("/api/board")
    public Board createBoard(@RequestBody BoardRequestDto boardRequestDto)
    {
      return boardService.saveBoard(boardRequestDto);
    }
    @GetMapping("/api/board/{boardId}")
    public Board getBoard(@PathVariable Long boardId)
    {
        return boardService.findBoard(boardId);
    }
    @GetMapping("/api/boards/search")
    public List<Board> searchBoardsByTitle(@RequestParam("title") String title) {
        BoardSerachRequestDTO boardSerachRequestDTO=new BoardSerachRequestDTO();
        boardSerachRequestDTO.setTitle(title);
        return boardService.searchBoards(boardSerachRequestDTO);
    }
    @PatchMapping("api/board/{boardId}")
    public Board updateBoard(@RequestBody BoardRequestDto boardRequestDto,@PathVariable Long boardId){
        return boardService.updateBoard(boardId, boardRequestDto);
    }
    //삭제 반환 DTO
    @DeleteMapping("/api/board/{boardId}")
    public ResponseEntity<ResoponseBody> deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
        return CpsResponse.toResponse(Status.SUCCESS); }
}
