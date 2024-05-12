package server.cps.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import server.cps.auth.service.LoginService;
import server.cps.board.dto.BoardDto;
import server.cps.board.dto.BoardRequestDto;
import server.cps.board.dto.BoardResponseDto;
import server.cps.board.dto.BoardSerachRequestDTO;
import server.cps.board.service.BoardService;
import server.cps.common.CpsResponse;
import server.cps.common.ResoponseBody;
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
    private final LoginService loginService;
    @GetMapping("/api/boards")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResoponseBody<PageResponse<BoardDto>>>getAllBoards(@PageableDefault(page = 0, size = 10)Pageable pageable) {
        List<BoardDto> boards = boardService.findAllBoards(pageable);
        Pageinfo pageinfo = new Pageinfo(pageable);

        PageResponse<BoardDto> pageResponse = PageResponse.<BoardDto>builder()
                .content(boards)
                .pageinfo(pageinfo)
                .build();
        return CpsResponse.toResponse(Status.READ,pageResponse);
    }
    @PostMapping("/api/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        // 사용자의 memberId 가져오기
        String memberId = userDetails.getUsername();
        Member member = memberSevice.findMemberWithLoginid(memberId);

        return boardService.saveBoard(boardRequestDto, member);
    }
    @GetMapping("/api/board/{boardId}")
    public Board getBoard(@PathVariable Long boardId)
    {
        return boardService.findBoard(boardId);
    }
    @GetMapping("/api/boards/search")
    public ResponseEntity<ResoponseBody<PageResponse<BoardDto>>> searchBoardsByTitle(@PageableDefault(page = 0, size = 10)Pageable pageable,@RequestParam("title") String title) {
        BoardSerachRequestDTO boardSerachRequestDTO=new BoardSerachRequestDTO();
        boardSerachRequestDTO.setTitle(title);
        List<BoardDto> searchBoards = boardService.searchBoards(pageable, boardSerachRequestDTO);
        Pageinfo pageinfo = new Pageinfo(pageable);
        PageResponse<BoardDto> pageResponse = PageResponse.<BoardDto>builder()
                .content(searchBoards)
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
    public ResponseEntity<ResoponseBody> deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
        return CpsResponse.toResponse(Status.SUCCESS); }
}
