package server.cps.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import server.cps.model.CompilationResult;
import server.cps.model.SubmissionResult;
import server.cps.service.CompileService;
import server.cps.service.ProblemService;

@Controller
public class ProblemController {
    @Autowired
    private CompileService compileService;

    @Autowired
    private ProblemService problemService;

    @PostMapping("/submit")
    public String submitCode(String code, String language,  Model model) {
        CompilationResult compilationResult = compileService.compile(code, language, null);
        //file을 input으로 바꿔주는 로직을 문제에서 구현
        //1. file 자체를 보내면 compileService에서 run을 오버로드 함
        //2. String 으로 바꾸면 input을 가변 파라미터로 바꾸기
        SubmissionResult submissionResult = problemService.submit(compilationResult);
        model.addAttribute("submissionResult", submissionResult);
        return "submissionResult";
    }
}
