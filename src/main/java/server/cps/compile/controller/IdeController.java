package server.cps.compile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.cps.common.CpsResponse;
import server.cps.common.ResponseBody;
import server.cps.common.Status;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.compile.service.CompilerSelectService;
import server.cps.exception.DockerException;
import server.cps.compile.dto.CompilationResult;

import java.io.IOException;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class IdeController {

    @Autowired
    private final CompilerSelectService compilerSelectService;

    @PostMapping("/api/compile")
    public ResponseEntity<ResponseBody<CompilationResult>> compileCode(@RequestBody CompileRequestDTO compileRequest)  {
        compileRequest.setUserName("Test");
        try {
            CompilationResult result = compilerSelectService.getCompilerForLanguage(compileRequest.getLanguage()).compileAndRun(compileRequest);
            return CpsResponse.toResponse(Status.RUN, result);
        } catch (IOException | InterruptedException | DockerException e) {
            throw new DockerException(e.toString(),500);
        }
    }

//@PostMapping("/api/compile")
//public CompilationResult compileCode(@RequestBody CompileRequestDTO compileRequest) {
//    // compile 및 실행 로직
//    CompilationResult c = testService.compile(compileRequest);
//    return testService.run(c, compileRequest.getInput(),compileRequest.getLanguage()); // JSON 형식의 데이터를 반환
//}

}

