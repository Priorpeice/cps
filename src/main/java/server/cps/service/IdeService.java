package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;
import server.cps.model.SubmissionResult;

import java.io.IOException;
import java.util.Map;

@Service
public class IdeService {
    private final Map<String, Compiler> languageCompilerMap;

    public IdeService(Map<String, Compiler> languageCompilerMap) {
        this.languageCompilerMap = languageCompilerMap;
    }


    public CompilationResult compileAndRun(String code, String language, String input) {
        Compiler selectedCompiler = getCompilerForLanguage(language);
        try {
            CompilationResult compileResult = selectedCompiler.compile(code, input);

            if (compileResult.isCompile()) {
                CompilationResult runResult = selectedCompiler.run(compileResult.getOutput(), input);
                return new CompilationResult(runResult.getOutput(), true);
            }
            return compileResult;
        } catch (IOException | InterruptedException e) {
            String errorMessage = "Exception during compilation: " + e.getMessage();
            return new CompilationResult(errorMessage, false); // 컴파일 실패로 표시
        }
    }

    private Compiler getCompilerForLanguage(String language) {
        Compiler compiler = languageCompilerMap.get(language);
        if (compiler != null) {
            return compiler;
        }
        throw new IllegalArgumentException("Unsupported language: " + language);
    }

}
