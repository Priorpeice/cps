package server.cps.infra;

import server.cps.model.CompilationResult;

import java.io.IOException;

public interface ProcessExecutor {
    public CompilationResult executeCommand(String[] command, String input) throws IOException, InterruptedException;
}
