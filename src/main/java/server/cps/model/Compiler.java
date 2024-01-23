package server.cps.model;

import java.io.IOException;

public interface Compiler {
    CompilationResult compile(String code, String input) throws IOException, InterruptedException;
    CompilationResult run(String code, String input) throws IOException, InterruptedException;


}
