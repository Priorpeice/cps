package server.cps.problem.dto;

import lombok.Getter;
import lombok.Setter;
import server.cps.compile.dto.Command;
import server.cps.model.CompilationResult;

import java.util.List;

@Getter
@Setter
public class SubmissionRequstDTO {
    private final String code;
    private final String language;
    private final String problemId;
    private String folderPath;
    private String userName;
    private Command command;
    private int numberOfFile;
    private List<CompilationResult> compilationResults;

    public SubmissionRequstDTO(String code, String language, String problemId) {
        this.code = code;
        this.language = language;
        this.problemId = problemId;
    }

}
