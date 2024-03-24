package server.cps.compile.compiler;

import server.cps.dto.compile.CompileRequestDTO;
import server.cps.dto.problem.ProblemRequstDTO;
import server.cps.model.CompilationResult;

import java.io.IOException;
import java.util.List;

public interface CompilerService {
    CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException;
    List<CompilationResult> testAndRun(ProblemRequstDTO problemRequstDTO) throws InterruptedException, IOException;

}
