package server.cps.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.model.CompilationResult;
import server.cps.model.Problem;
import server.cps.model.SubmissionResult;
import server.cps.service.CompileService;
import server.cps.service.ProblemService;

import java.util.List;

@Controller
public class ProblemController {
    @Autowired
    private CompileService compileService;

    @Autowired
    private ProblemService problemService;

    @PostMapping("/submit")
    public String submitCode(String code, String language, String problem, Model model , CompileRequestDTO compileRequestDTO) {
        //1. compile 하기
        CompilationResult compilationResult = compileService.compile(compileRequestDTO);
        //2. testcase 만큼 run
        List<CompilationResult> runResult= compileService.runs(compilationResult,language, problem);
        //3. 결과를 받아서 채점하기
        SubmissionResult submissionResult = problemService.submit(runResult,problem);
        model.addAttribute("submissionResult", submissionResult);
        return "submissionResult";
    }
    @PostMapping("/create")
    public String createProblem(@RequestParam("title") String title,
                                @RequestParam("description") String description,
                                @RequestParam("code") String code,
                                @RequestParam("input") String input,
                                @RequestParam("output") String output,
                                @RequestParam("sampleInput") String sampleInput,
                                @RequestParam("sampleOutput") String sampleOutput) {

        Problem problem = new Problem(title, description, code, input, output, sampleInput, sampleOutput);
        problemService.createProblem(problem);

        return "redirect:/";
    }
    @GetMapping("/create")
    public String showCreateForm(){
        return "createProblem";
    }

    @GetMapping("/list")
    public String showProblemList(Model model) {
        List<Problem> problems = problemService.getAllProblems();
        model.addAttribute("problems", problems);
        return "problemList";
    }


}
