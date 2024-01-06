package server.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import server.cps.model.CompilationResult;
import server.cps.model.SubmissionResult;
import server.cps.service.IdeService;

@Controller
public class IdeController {
    @Autowired
    private IdeService ideService;

    @RequestMapping("/ide")
    public String ide(){
        return "ide";
    }

    @PostMapping("/compile")
    public String compileCode(String code, String language, @RequestParam(required = false)String input, Model model) {
        CompilationResult compilationResult = ideService.compileAndRun(code, language ,input);
        model.addAttribute("compilationResult", compilationResult);
        return "result";
    }

    @PostMapping("/submit")
    public String submitCode(String code, String language, Model model) {
        SubmissionResult submissionResult = ideService.submitCode(code, language);
        model.addAttribute("submissionResult", submissionResult);
        return "result";
    }
}
