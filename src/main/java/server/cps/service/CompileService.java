package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;
import server.cps.respository.FileRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CompileService {
    private final Map<String, Compiler> languageCompilerMap;
    private final FileRepository fileRepository;

    public CompileService(Map<String, Compiler> languageCompilerMap, FileRepository fileRepository) {
        this.languageCompilerMap = languageCompilerMap;
        this.fileRepository = fileRepository;
    }

    public CompilationResult compile(String code, String language, String input) {
        Compiler selectedCompiler = getCompilerForLanguage(language);
        try {
            // 파일 저장
            String fileName = fileRepository.generateFileName(language);
            fileRepository.writeStringToFile(code, fileName);

            // 컴파일
            CompilationResult compileResult = selectedCompiler.compile(fileName,input);
            return compileResult;

        } catch (IOException | InterruptedException e) {
            String errorMessage = "Exception during compilation: " + e.getMessage();
            return new CompilationResult(errorMessage, false); // 컴파일 실패로 표시
        }
    }

    public CompilationResult run(CompilationResult compileResult, String input,String language) {
        Compiler selectedCompiler = getCompilerForLanguage(language);
        try {
            if (compileResult.isCompile()) {
                CompilationResult runResult = selectedCompiler.run(compileResult.getOutput(), input);
                return new CompilationResult(runResult.getOutput(), true);
            }
            return compileResult;
        } catch (IOException | InterruptedException e) {
            String errorMessage = "Exception during running: " + e.getMessage();
            return new CompilationResult(errorMessage, false); // 실행 실패로 표시
        }
    }

    public List<CompilationResult> runs(CompilationResult compileResult, String language,String problem) {
        Compiler selectedCompiler = getCompilerForLanguage(language);
        List<CompilationResult> runResults = new ArrayList<>();
        try {
            if (compileResult.isCompile()) {
                List<String> inputs = fileRepository.readInputsFromFiles(problem);

                for (String input : inputs) {
                    CompilationResult runResult = selectedCompiler.run(compileResult.getOutput(), input);
                    runResults.add(runResult);
                }
            }
        } catch (IOException | InterruptedException e) {
            String errorMessage = "Exception during running: " + e.getMessage();
            runResults.add(new CompilationResult(errorMessage, false)); // 실행 실패로 표시
        }
        return runResults;
    }

    private Compiler getCompilerForLanguage(String language) {
        Compiler compiler= languageCompilerMap.get(language);
        if(compiler !=null){
            return compiler;
        }
        throw new IllegalArgumentException("Unsupported Language" + language);
    }
}
