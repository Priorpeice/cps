package server.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import server.cps.model.CompilationResult;
import server.cps.service.CompileService;

@Controller
public class IdeController {
    @Autowired
    private CompileService compileService;

    @GetMapping("/ide")
    public String ide(){
        return "ide";
    }

    @PostMapping("/compile")
    public String compileCode(String code, String language, @RequestParam(required = false)String input, Model model) {
        CompilationResult c = compileService.compile(code, language ,input);
        CompilationResult compilationResult= compileService.run(c,input,language);
        model.addAttribute("compilationResult", compilationResult);
        return "result";
    }

}
