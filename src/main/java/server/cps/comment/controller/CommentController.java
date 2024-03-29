package server.cps.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.cps.board.service.BoardService;
import server.cps.comment.dto.CommentRequestDto;
import server.cps.comment.service.CommentService;
import server.cps.entity.Board;
import server.cps.entity.Comment;

@RestController
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardService boardService;
    /**/
    @PostMapping("/api/board/{boardId}")
    public Comment createComment(@RequestBody CommentRequestDto commentRequestDto , @PathVariable Long boardId){
        Board board =boardService.showBoard(boardId);
        Comment comment = commentService.saveComment(commentRequestDto,board);
        return comment;
    } @GetMapping("/api/board/{boardId}/{commentId}")
    public  Comment deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return null;
    }

}
