package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.infra.ProcessExecutor;
import server.cps.infra.ProcessExecutorImpl;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import java.io.*;


@Service("py")
public class PythonRunner implements Compiler {
    private final ProcessExecutor processExecutor;
    public PythonRunner(ProcessExecutor processExecutor) {
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