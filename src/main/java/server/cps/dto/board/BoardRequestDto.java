package server.cps.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.cps.entity.Board;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BoardRequestDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime uptDate;
    private Long viewCount;
    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
