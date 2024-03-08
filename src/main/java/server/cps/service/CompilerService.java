package server.cps.service;

import server.cps.dto.compile.CompileRequestDTO;
import server.cps.model.CompilationResult;

import java.io.IOException;

public interface CompilerService {
    CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException;



}
