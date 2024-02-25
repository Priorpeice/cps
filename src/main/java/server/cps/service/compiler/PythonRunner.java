package server.cps.service.compiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.cps.infra.ProcessExecutor;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import java.io.IOException;

@Component("py")
public class PythonRunner implements Compiler {
    private final ProcessExecutor processExecutor;
    @Autowired
    public PythonRunner( ProcessExecutor processExecutor) {
        this.processExecutor = processExecutor;
    }

    @Override
    public CompilationResult compile(String fileName, String input) throws IOException, InterruptedException {
     return new CompilationResult(fileName,true);
    }
    @Override
    public CompilationResult run(String fileName, String input) throws IOException, InterruptedException {
        String[] command = {"python", fileName};
        return processExecutor.executeCommand(command,input);
    }

}