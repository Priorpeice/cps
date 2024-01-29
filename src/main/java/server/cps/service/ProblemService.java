package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;
import server.cps.model.SubmissionResult;

import java.util.Map;

@Service
public class ProblemService {
    private final Map<String, Compiler> languageCompilerMap;

    public ProblemService(Map<String, Compiler> languageCompilerMap) {
        this.languageCompilerMap = languageCompilerMap;
    }

    public SubmissionResult submit(CompilationResult compilationResult) {
        int score = compilationResult.isCompile() ? 100 : 0;
        return new SubmissionResult(compilationResult,score);
    }
    //채점 로직
}

