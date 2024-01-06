package server.cps.model;

public class SubmissionResult {
    private CompilationResult compilationResult;
    private int score;

    public SubmissionResult(CompilationResult compilationResult) {
        this.compilationResult = compilationResult;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public CompilationResult getCompilationResult() {
        return compilationResult;
    }

    public void setCompilationResult(CompilationResult compilationResult) {
        this.compilationResult = compilationResult;
    }
}
