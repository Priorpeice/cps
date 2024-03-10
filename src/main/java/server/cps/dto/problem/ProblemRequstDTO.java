package server.cps.dto.problem;

import lombok.Getter;
import lombok.Setter;
import server.cps.dto.compile.Command;
import server.cps.model.CompilationResult;

import java.util.List;

@Getter
@Setter
public class ProblemRequstDTO {
    private final String code;
    private final String language;
    private final String problemId;
    private String folderPath;
    private String userName;
    private Command command;
    private int numberOfFile;
    private List<CompilationResult> compilationResults;

    public ProblemRequstDTO(String code, String language, String problemId) {
        this.code = code;
        this.language = language;
        this.problemId = problemId;
    }

}
