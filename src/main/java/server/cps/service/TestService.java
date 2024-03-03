package server.cps.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.model.CompilationResult;
import server.cps.respository.CodeRepository;
import server.cps.respository.DockerRepository;
import server.cps.service.compiler.TestCompiler;

import java.io.File;
import java.io.IOException;
@Service
public class TestService {
    private final TestCompiler TestCompiler;
    private final CodeRepository codeRepository;
    private final DockerRepository dockerRepository;

    public TestService(@Qualifier("Test") TestCompiler compiler, CodeRepository codeRepository, DockerRepository dockerRepository) {
        this.TestCompiler= compiler;
        this.codeRepository = codeRepository;
        this.dockerRepository = dockerRepository;
    }

    public CompilationResult compile(CompileRequestDTO compileRequestDTO) {
        try {
            // 파일 저장
            String fileName = codeRepository.generateFileName(compileRequestDTO.getLanguage());
            codeRepository.writeStringToFile(compileRequestDTO.getCode(), fileName);

            File file = dockerRepository.generateDockerfile(compileRequestDTO.getLanguage(), fileName, null, compileRequestDTO.getLanguage());
            // 컴파일
            CompilationResult compilationResult = TestCompiler.compile(file);

            return compilationResult ;

        } catch (IOException e) {
            String errorMessage = "Exception during compilation: " + e.getMessage();
            return new CompilationResult(errorMessage, false); // 컴파일 실패로 표시
        }
    }
    public CompilationResult run(CompilationResult compileResult, String input ,String language) {
        if (compileResult.isCompile()) {
            CompilationResult runResult = TestCompiler.run(compileResult,"Main",input ,language);
            return new CompilationResult(runResult.getOutput(), true);
        }
        return compileResult;
    }
   }


