package server.cps.problem.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.cps.entity.Problem;
import server.cps.problem.dto.ProblemRequestDTO;
import server.cps.problem.dto.ProblemSearchResponseDTO;
import server.cps.problem.service.ProblemService;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProblemController {

    private  final ProblemService problemService;
    @GetMapping("/api/problems")
    public List<ProblemSearchResponseDTO> showProblems(){
        return  problemService.findIdAndTitle();
    }
    @GetMapping("/api/problem/{problemId}")
    public Problem getBoard(@PathVariable Long problemId)
    {
        return problemService.findById(problemId);
    }
    @PostMapping("/api/problem")
    public Problem createProblem(@RequestBody ProblemRequestDTO problemRequestDTO)
    {
        return problemService.saveProblem(problemRequestDTO);
    }



}
