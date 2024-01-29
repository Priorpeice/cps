package server.cps.model;

public class CompilationResult {
    private String output;
    private final boolean isCompile;

    public CompilationResult(String output, boolean isCompile) {
        this.output = output;
        this.isCompile = isCompile;
    }

    public String getOutput() {
        return output;
    }
    public boolean isCompile() {
        return isCompile;
    }
    public void setOutput(String output) {
        this.output = output;
    }
}
