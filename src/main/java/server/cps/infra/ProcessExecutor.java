package server.cps.infra;

import server.cps.compile.dto.CompileRequestDTO;
import server.cps.submission.dto.SubmissionRequstDTO;
import server.cps.compile.dto.CompilationResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ProcessExecutor {
    public CompilationResult executeCompile(File file) throws IOException, InterruptedException;
    public CompilationResult executeRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException;
    List<CompilationResult> executeRuns(SubmissionRequstDTO problemRequstDTO) throws IOException, InterruptedException;
}
