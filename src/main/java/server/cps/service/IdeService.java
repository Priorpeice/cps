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
            if (input == null) {
                return selectedCompiler.compileAndRun(code);
            } else {
                return selectedCompiler.compileAndRun(code, input);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    public SubmissionResult submitCode(String code, String language) {
        Compiler selectedCompiler = getCompilerForLanguage(language);
        try {
            CompilationResult compilationResult = selectedCompiler.compileAndRun(code);

            int score = calculateScore(code);

            SubmissionResult submissionResult = new SubmissionResult(compilationResult);
            submissionResult.setScore(score);

            return submissionResult;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Compiler getCompilerForLanguage(String language) {
        Compiler compiler = languageCompilerMap.get(language);
        if (compiler != null) {
            return compiler;
        }

        throw new IllegalArgumentException("Unsupported language: " + language);
    }

    private int calculateScore(String code) {
        return code.length() / 10;
    }
}
