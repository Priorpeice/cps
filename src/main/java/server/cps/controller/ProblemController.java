package server.cps.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.cps.dto.problem.Problem;
import server.cps.dto.problem.ProblemRequstDTO;
import server.cps.model.SubmissionResult;
import server.cps.service.CompilerSelectService;
import server.cps.service.SubmitService;

import java.io.IOException;

@RestController
@CrossOrigin
public class ProblemController {
    @Autowired
    private CompilerSelectService compilerSelectService;
    @Autowired
    private SubmitService submitService;
    @GetMapping("/api/problems")
    public Problem problem( Problem problem){
        return  null;
    }
    @PostMapping("/api/submit")
    public SubmissionResult markCode(@RequestBody ProblemRequstDTO problemRequstDTO) {
        problemRequstDTO.setUserName("Test");
        try {
            problemRequstDTO.setCompilationResults(compilerSelectService.getCompilerForLanguage(problemRequstDTO.getLanguage()).testAndRun(problemRequstDTO));
            return submitService.mark(problemRequstDTO);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
