package server.cps.board.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import server.cps.board.dto.BoardDto;
import server.cps.comment.dto.CommentDto;
import server.cps.entity.Board;

import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class BoardMapper {
    public  BoardDto toDto(Board board) {
        BoardDto dto = new BoardDto();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setMemberNickname(board.getMember().getNickname());
        return dto;
    }
    public  BoardDto toDto(Board board, List<CommentDto> commentDtos) {
        BoardDto dto = new BoardDto();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setMemberNickname(board.getMember().getNickname());
        dto.setComments(commentDtos);
        return dto;
    }

    public  List<BoardDto> toDtoList(Page<Board> boards) {
        return boards.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
