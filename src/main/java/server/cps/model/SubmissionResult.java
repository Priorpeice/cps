package server.cps.model;

import java.util.List;

public class SubmissionResult {
    private final List<CompilationResult> compilationResults;
    private final int score;

    private Boolean success;

    public SubmissionResult(List<CompilationResult> compilationResults, int score, boolean success) {
        this.compilationResults = compilationResults;
        this.score = score;
        this.success =success;
    }

    public List<CompilationResult> getCompilationResults() {
        return compilationResults;
    }

    public int getScore() {
        return score;
    }

    public Boolean getSuccess() {
        return success;
    }
}
