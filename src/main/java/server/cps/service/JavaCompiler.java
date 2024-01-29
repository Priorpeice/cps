package server.cps.service;

import org.springframework.stereotype.Service;

import server.cps.infra.ProcessExecutor;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import javax.tools.*;
import java.io.*;


@Service("java")
public class JavaCompiler implements Compiler {
    private final ProcessExecutor processExecutor;
    public JavaCompiler(ProcessExecutor processExecutor) {
        this.processExecutor=processExecutor;
    }

    @Override
    public CompilationResult compile(String fileName, String input) throws IOException, InterruptedException {
        return  compileJavaFile(fileName);
    }

    private static CompilationResult compileJavaFile(String fileName) throws IOException{
            javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

            try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
                Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(fileName);
                javax.tools.JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

                if (!task.call()) {
                    StringBuilder errorOutput = new StringBuilder("Compilation failed:\n");
                    for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                        errorOutput.append(String.format("Error on line %d in %s%n",
                                diagnostic.getLineNumber(),
                                diagnostic.getSource().toUri()));
                        errorOutput.append(diagnostic.getMessage(null)).append("\n");
                    }
                    return new CompilationResult(errorOutput.toString(), false);
                }
            }
        return new CompilationResult(fileName, true);
    }

    @Override
    public CompilationResult run(String fileName, String input) throws IOException, InterruptedException {
        String[] command = {"java", fileName};
        return processExecutor.executeCommand(command, input);
    }

    }

