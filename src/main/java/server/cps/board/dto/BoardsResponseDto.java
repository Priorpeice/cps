package server.cps.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class BoardsResponseDto {
    private String BoardId;
    private String title;
    private String content;
    private String memberId;
    @Builder
    public BoardsResponseDto(String boardId, String title, String content, String memberId) {
        this.BoardId = boardId;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }
}
