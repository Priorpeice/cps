package server.cps.compile.compiler;

import server.cps.compile.dto.CompileRequestDTO;
import server.cps.submission.dto.SubmissionRequstDTO;
import server.cps.model.CompilationResult;

import java.io.IOException;
import java.util.List;

public interface CompilerService {
    CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException;
    List<CompilationResult> testAndRun(SubmissionRequstDTO problemRequstDTO) throws InterruptedException, IOException;

}
