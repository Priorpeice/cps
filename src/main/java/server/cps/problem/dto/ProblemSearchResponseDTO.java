package server.cps.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.cps.entity.Problem;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSearchResponseDTO {
    private Long id;
    private String title;

    public static ProblemSearchResponseDTO toDTO(Problem problem) {
       return ProblemSearchResponseDTO.builder()
               .id(problem.getId())
               .title(problem.getTitle())
               .build();
    }

}
