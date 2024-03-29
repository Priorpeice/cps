package server.cps.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.cps.entity.Board;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BoardRequestDto {
    private String title;
    private String content;
    private Long viewCount;
    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
    public BoardRequestDto(String title, String content)
    {
        this.title=title;
        this.content=content;
    }
}
