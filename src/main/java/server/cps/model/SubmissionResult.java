package server.cps.model;

public class SubmissionResult {
    private final CompilationResult compilationResult;
    private final int score;

    public SubmissionResult(CompilationResult compilationResult, int score) {
        this.compilationResult = compilationResult;
        this.score=score;
    }
    public CompilationResult getCompilationResult() {
        return compilationResult;
    }
    public int getScore() {
        return score;
    }





}
