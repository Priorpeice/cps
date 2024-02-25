package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;
import server.cps.respository.CodeRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CompileService {
    private final Map<String, Compiler> languageCompilerMap;
    private final CodeRepository codeRepository;

    public CompileService(Map<String, Compiler> languageCompilerMap, CodeRepository codeRepository) {
        this.languageCompilerMap = languageCompilerMap;
        this.codeRepository = codeRepository;
    }

    public CompilationResult compile(CompileRequestDTO compileRequestDTO) {
        Compiler selectedCompiler = getCompilerForLanguage(compileRequestDTO.getLanguage());
        try {
            // 파일 저장
            String fileName = codeRepository.generateFileName(compileRequestDTO.getLanguage());
            codeRepository.writeStringToFile(compileRequestDTO.getCode(), fileName);

            // 컴파일
            return selectedCompiler.compile(fileName, compileRequestDTO.getInput());

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
                List<String> inputs = codeRepository.readFilesFromFolder(problem,".in");
                for (String input : inputs) {
                    CompilationResult runResult = selectedCompiler.run(compileResult.getOutput(), input);
                    runResults.add(runResult);
                }
            }
            else {
                runResults.add(new CompilationResult("fail", false));
            }
        } catch (IOException | InterruptedException e) {
            String errorMessage = "Exception during running: " + e.getMessage();
            runResults.add(new CompilationResult(errorMessage, false));
        }
        return runResults;
    }

    public Compiler getCompilerForLanguage(String language) {

        Compiler compiler= languageCompilerMap.get(language);
        System.out.println(compiler);
        if(compiler !=null){
            return compiler;
        }
        throw new IllegalArgumentException("Unsupported Language " + language);
    }
}
