package server.cps.comment.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.cps.comment.dto.CommentDto;
import server.cps.entity.Comment;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class CommentMapper {
    public static CommentDto toDto(Comment comment)
    {
        CommentDto dto = new CommentDto();
        dto.setCommentId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setRegDate(comment.getRegDate());
        dto.setNickname(comment.getMember().getNickname());
        return dto;
    }
    public List<CommentDto> toDtoList(List<Comment> comments) {
        List<CommentDto> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            dtos.add(toDto(comment));
        }
        return dtos;
    }

}
