package server.cps.board.mapper;

import server.cps.board.dto.BoardDto;
import server.cps.entity.Board;

public class BoardMapper {
    public static BoardDto toDto(Board board) {
        BoardDto dto = new BoardDto();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setMemberNickname(board.getMember().getNickname());
        return dto;
    }
}
