package server.cps.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.cps.entity.Board;
import server.cps.entity.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BoardRequestDto {
    private String title;
    private String content;
    private Long viewCount;

    public Board toEntity(Member member) {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .member(member)
                .build();
    }
    public BoardRequestDto(String title, String content)
    {
        this.title=title;
        this.content=content;
    }
}
