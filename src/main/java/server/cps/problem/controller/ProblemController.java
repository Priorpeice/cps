package server.cps.problem.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.cps.common.CpsResponse;
import server.cps.common.ResoponseBody;
import server.cps.common.Status;
import server.cps.compile.service.CompilerSelectService;
import server.cps.entity.Problem;
import server.cps.model.SubmissionResult;
import server.cps.problem.dto.ProblemRequestDTO;
import server.cps.problem.dto.ProblemSearchResponseDTO;
import server.cps.problem.dto.SubmissionRequstDTO;
import server.cps.problem.service.ProblemService;
import server.cps.problem.service.SubmitService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProblemController {
    private final CompilerSelectService compilerSelectService;
    private final SubmitService submitService;
    private  final ProblemService problemService;
    @GetMapping("/api/problems")
    public List<ProblemSearchResponseDTO> showProblems(){
        return  problemService.findIdAndTitle();
    }
    @PostMapping("/api/problem")
    public Problem createProblem(@RequestBody ProblemRequestDTO problemRequestDTO)
    {
        return problemService.saveProblem(problemRequestDTO);
    }
    @PostMapping("/api/submit")
    public ResponseEntity<ResoponseBody<SubmissionResult>> markCode(@RequestBody SubmissionRequstDTO submissionRequstDTO) {
        submissionRequstDTO.setUserName("Test");
        try {
            submissionRequstDTO.setCompilationResults(compilerSelectService.getCompilerForLanguage(submissionRequstDTO.getLanguage()).testAndRun(submissionRequstDTO));
            return CpsResponse.toResponse(Status.SUCCESS,submitService.mark(submissionRequstDTO));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
