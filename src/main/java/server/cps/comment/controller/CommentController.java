package server.cps.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import server.cps.board.service.BoardService;
import server.cps.comment.dto.CommentRequestDto;
import server.cps.comment.service.CommentService;
import server.cps.entity.Board;
import server.cps.entity.Comment;
import server.cps.entity.Member;
import server.cps.member.service.MemberSevice;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;
    private final MemberSevice memberSevice;
    /**/
    @PostMapping("/api/board/{boardId}")
    public Comment createComment(@RequestBody CommentRequestDto commentRequestDto , @PathVariable Long boardId,@AuthenticationPrincipal UserDetails userDetails){
        String memberId = userDetails.getUsername();
        Member member = memberSevice.findMemberWithLoginid(memberId);
        Board board =boardService.findBoard(boardId);
        Comment comment = commentService.saveComment(commentRequestDto,board,member);
        return comment;
    } @GetMapping("/api/board/{boardId}/{commentId}")
    public  Comment deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return null;
    }


    @GetMapping("/api/comment/{commentId}")
    public  Comment showComment(@PathVariable Long commentId)
    {
        return commentService.showComment(commentId);
    }

}
