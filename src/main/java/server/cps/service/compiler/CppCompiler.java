package server.cps.service.compiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.cps.infra.ProcessExecutor;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import java.io.IOException;


@Component("cpp")
public class CppCompiler implements Compiler {

    private final ProcessExecutor processExecutor;
    @Autowired
    public CppCompiler(ProcessExecutor processExecutor) {
        this.processExecutor = processExecutor;
    }

    @Override
    public CompilationResult compile(String fileName, String input) throws IOException, InterruptedException {
        String compileCommand = "gcc++ " + fileName + " -o temp";
        CompilationResult compilationResult = processExecutor.executeCommand(compileCommand.split("\\s+"), input);

        if (!compilationResult.isCompile() || compilationResult.getOutput().contains("error")) {
            return new CompilationResult("Compilation failed:\n" + compilationResult.getOutput(), false);
        }

        return new CompilationResult("temp", true); // fileName
    }

    @Override
    public CompilationResult run(String fileName, String input) throws IOException, InterruptedException {
        String runCommand = "./" + fileName;

        return processExecutor.executeCommand(new String[] { runCommand }, input);
    }



}
