package server.cps.model;

public class CompilationResult {
    private String output;
    private final boolean isCompile;
    private String runTime;

    public CompilationResult(String output, boolean isCompile) {
        this.output = output;
        this.isCompile = isCompile;
    }
    public void setRunTime(String runTime){
        this.runTime =runTime;
    }
    public String getRunTime(){return runTime;}
    public String getOutput() {
        return output;
    }
    public boolean isCompile() {
        return isCompile;
    }

}
