package server.cps.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.cps.entity.Board;
import server.cps.entity.Comment;
import server.cps.entity.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    private String content;
    public Comment toEntity(Board board, Member member) {
        return Comment.builder()
                .content(this.content)
                .board(board)
                .member(member)
                .build();
    }
public Comment toEntity(Board board) {
    return Comment.builder()
            .content(this.content)
            .board(board)
            .build();
}
}
