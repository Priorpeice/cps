package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import javax.tools.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

@Service("java")
public class JavaCompiler implements Compiler {

    @Override
    public CompilationResult compileAndRun(String code) throws IOException, InterruptedException {
        return compileAndRun(code, null);
    }

    @Override
    public CompilationResult compileAndRun(String code, String input) throws IOException, InterruptedException {
        // 1. 컴파일
        String className = "Main";
        String fileName = className + ".java";

        // 코드를 파일에 쓰기
        writeStringToFile(code, fileName);

        // 컴파일
        compileJavaFile(fileName);

        // 2. 실행
        String[] command = {"java", className};
        return executeCommand(command, input);
    }

    private static void writeStringToFile(String content, String fileName) throws IOException {
        Files.write(Paths.get(fileName), Collections.singleton(content), StandardCharsets.UTF_8);
    }

    private static void compileJavaFile(String fileName) throws IOException {
        javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(fileName);
            javax.tools.JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

            if (!task.call()) {
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    System.err.format("Error on line %d in %s%n",
                            diagnostic.getLineNumber(),
                            diagnostic.getSource().toUri());
                    System.err.println(diagnostic.getMessage(null));
                }
                throw new RuntimeException("Compilation failed");
            }
        }
    }

    private static CompilationResult executeCommand(String[] command, String input) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        // Input handling
        if (input != null && !input.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(input);
                writer.flush();
            }
        }

        // Output handling
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();

            // Check exit code and handle errors
            if (exitCode != 0) {
                output.append("fail").append("\n");
            }

            return new CompilationResult(output.toString());
        }
    }
}
