package server.cps.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import server.cps.board.dto.BoardDto;
import server.cps.board.dto.BoardRequestDto;
import server.cps.board.dto.BoardsResponseDto;
import server.cps.board.dto.BoardSerachRequestDTO;
import server.cps.board.mapper.BoardMapper;
import server.cps.board.service.BoardService;
import server.cps.comment.dto.CommentDto;
import server.cps.comment.mapper.CommentMapper;
import server.cps.common.CpsResponse;
import server.cps.common.ResponseBody;
import server.cps.common.Status;
import server.cps.common.page.PageResponse;
import server.cps.common.page.Pageinfo;
import server.cps.entity.Board;
import server.cps.entity.Member;
import server.cps.member.service.MemberSevice;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
//@RequestMapping()
public class BoardController {
    @Autowired
    private final BoardService boardService;
    private final MemberSevice memberSevice;
    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;
    @GetMapping("/api/boards")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseBody<PageResponse<BoardDto>>>getAllBoards(@PageableDefault(page = 0, size = 10)Pageable pageable) {
        Page<Board> boards = boardService.findAllBoards(pageable);
        Pageinfo pageinfo = new Pageinfo(boards,pageable);
        List<BoardDto> boardDtoList = boardMapper.toDtoList(boards);
        PageResponse<BoardDto> pageResponse = PageResponse.<BoardDto>builder()
                .content(boardDtoList)
                .pageinfo(pageinfo)
                .build();
        return CpsResponse.toResponse(Status.READ,pageResponse);
    }
    @PostMapping("/api/board")
    public BoardsResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        // 사용자의 memberId 가져오기
        String memberId = userDetails.getUsername();
        Member member = memberSevice.findMemberWithLoginid(memberId);

        return boardService.saveBoard(boardRequestDto, member);
    }
    @GetMapping("/api/board/{boardId}")
    public ResponseEntity<ResponseBody<BoardDto>> getBoard(@PathVariable Long boardId)
    {
        Board board = boardService.findBoard(boardId);
        List<CommentDto> commentDto = commentMapper.toDtoList(board.getComments());
        BoardDto dto = boardMapper.toDto(board, commentDto);
        return CpsResponse.toResponse(Status.READ,dto);
    }
    @GetMapping("/api/boards/search")
    public ResponseEntity<ResponseBody<PageResponse<BoardDto>>> searchBoardsByTitle(@PageableDefault(page = 0, size = 10)Pageable pageable, @RequestParam("title") String title) {
        BoardSerachRequestDTO boardSerachRequestDTO=new BoardSerachRequestDTO();
        boardSerachRequestDTO.setTitle(title);
        Page<Board> boards = boardService.searchBoards(pageable, boardSerachRequestDTO);
        Pageinfo pageinfo = new Pageinfo(boards,pageable);
        List<BoardDto> boardDtoList = boardMapper.toDtoList(boards);
        PageResponse<BoardDto> pageResponse = PageResponse.<BoardDto>builder()
                .content(boardDtoList)
                .pageinfo(pageinfo)
                .build();
        return CpsResponse.toResponse(Status.READ,pageResponse);
    }
    @PatchMapping("api/board/{boardId}")
    public Board updateBoard(@RequestBody BoardRequestDto boardRequestDto,@PathVariable Long boardId){
        return boardService.updateBoard(boardId, boardRequestDto);
    }
    //삭제 반환 DTO
    @DeleteMapping("/api/board/{boardId}")
    public ResponseEntity<ResponseBody> deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
        return CpsResponse.toResponse(Status.SUCCESS); }
}
