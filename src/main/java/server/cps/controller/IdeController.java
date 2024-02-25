package server.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.model.CompilationResult;
import server.cps.service.CompileService;

@RestController
@CrossOrigin
public class IdeController {
    @Autowired
    private CompileService compileService;

//    @GetMapping("/ide")
//    public String ide(){
//        return "ide";
//    }
    @PostMapping("/api/compile")
    public CompilationResult compileCode(@RequestBody CompileRequestDTO compileRequest) {
        // compile 및 실행 로직
        CompilationResult c = compileService.compile(compileRequest);
        return compileService.run(c, compileRequest.getInput(), compileRequest.getLanguage()); // JSON 형식의 데이터를 반환
    }

}

