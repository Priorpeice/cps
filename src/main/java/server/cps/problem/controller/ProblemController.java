package server.cps.problem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.cps.common.CpsResponse;
import server.cps.common.ResoponseBody;
import server.cps.common.Status;
import server.cps.compile.service.CompilerSelectService;
import server.cps.problem.dto.Problem;
import server.cps.problem.dto.ProblemRequstDTO;
import server.cps.model.SubmissionResult;
import server.cps.problem.service.SubmitService;

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
    public ResponseEntity<ResoponseBody<SubmissionResult>> markCode(@RequestBody ProblemRequstDTO problemRequstDTO) {
        problemRequstDTO.setUserName("Test");
        try {
            problemRequstDTO.setCompilationResults(compilerSelectService.getCompilerForLanguage(problemRequstDTO.getLanguage()).testAndRun(problemRequstDTO));
            return CpsResponse.toResponse(Status.SUCCESS,submitService.mark(problemRequstDTO));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
