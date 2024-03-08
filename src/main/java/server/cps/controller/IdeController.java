package server.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.cps.dto.compile.CompileRequestDTO;
import server.cps.model.CompilationResult;
import server.cps.service.CompilerSelectService;

import java.io.IOException;

@RestController
@CrossOrigin
public class IdeController {
//    @Autowired
//    private CompileService compileService;

    @Autowired
    private CompilerSelectService compilerSelectService;

//    @GetMapping("/ide")
//    public String ide(){
//        return "ide";
//    }
    @PostMapping("/api/compile")
    public CompilationResult compileCode(@RequestBody CompileRequestDTO compileRequest) {
        compileRequest.setUserName("Test");
        try {
            return compilerSelectService.getCompilerForLanguage(compileRequest.getLanguage()).compileAndRun(compileRequest) ; // JSON 형식의 데이터를 반환
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//@PostMapping("/api/compile")
//public CompilationResult compileCode(@RequestBody CompileRequestDTO compileRequest) {
//    // compile 및 실행 로직
//    CompilationResult c = testService.compile(compileRequest);
//    return testService.run(c, compileRequest.getInput(),compileRequest.getLanguage()); // JSON 형식의 데이터를 반환
//}

}

