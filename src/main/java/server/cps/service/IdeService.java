package server.cps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;
import server.cps.model.SubmissionResult;

import java.io.IOException;

@Service
public class IdeService {

    private final Compiler cCompiler;
    private final Compiler pythonCompiler;

    public IdeService(@Qualifier("cCompiler") Compiler cCompiler,
                      @Qualifier("pythonCompiler") Compiler pythonCompiler) {
        this.cCompiler = cCompiler;
        this.pythonCompiler = pythonCompiler;
    }
    public CompilationResult compileAndRun(String code, String language, String input) {
        Compiler selectedCompiler = getCompilerForLanguage(language);

        try {
            if (input == null) {
                // No input provided by the user
                return selectedCompiler.compileAndRun(code);
            } else {
                // Input provided by the user
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
        if ("c".equalsIgnoreCase(language)) {
            return cCompiler;
        } else if ("python".equalsIgnoreCase(language)) {
            return pythonCompiler;
        }

        throw new IllegalArgumentException("Unsupported language: " + language);
    }

    private int calculateScore(String code) {
        return code.length() / 10;
    }
}
