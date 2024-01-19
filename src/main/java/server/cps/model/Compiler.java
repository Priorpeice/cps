package server.cps.model;

import java.io.IOException;

public interface Compiler {
    CompilationResult compileAndRun(String code) throws IOException, InterruptedException;
    CompilationResult compileAndRun(String code, String input) throws IOException, InterruptedException;


}
