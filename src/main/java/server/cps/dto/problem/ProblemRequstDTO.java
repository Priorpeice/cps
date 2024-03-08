package server.cps.dto.problem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemRequstDTO {
    private String code;
    private String language;
    private Problem problem;

    public ProblemRequstDTO(String code, String language, Problem problem) {
        this.code = code;
        this.language = language;
        this.problem = problem;
    }
}
