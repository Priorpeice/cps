package server.cps.infra;

import server.cps.dto.compile.CompileRequestDTO;
import server.cps.model.CompilationResult;

import java.io.IOException;

public interface ProcessExecutor {
    public CompilationResult executeCompile(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException;
    public CompilationResult executeRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException;
}
