package server.cps.problem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import server.cps.entity.Problem;
import server.cps.problem.dto.ProblemSearchResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProblemMapper {
    public static ProblemSearchResponseDTO toDto(Problem problem) {
        return ProblemSearchResponseDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .build();
    }

    public  List<ProblemSearchResponseDTO> toDtoList(Page<Problem> problems) {
        return problems.getContent().stream()
                .map(ProblemMapper::toDto)
                .collect(Collectors.toList());
    }
}
