package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import java.io.*;

@Service("c")
public class CCompiler implements Compiler {
    @Override
    public CompilationResult compileAndRun(String code) throws IOException, InterruptedException {
        return compileAndRun(code, null);
    }

    @Override
    public CompilationResult compileAndRun(String code, String input) throws IOException, InterruptedException {
        String fileName = "temp.c";
        writeStringToFile(code, fileName);

        String compileCommand = "gcc " + fileName + " -o temp";
        String compileOutput = executeCommand(compileCommand);

        if (compileOutput.contains("error")) {
            return new CompilationResult("Compilation failed:\n" + compileOutput);
        }

        String runCommand = "./temp";

        Process process = Runtime.getRuntime().exec(runCommand);

        if (input != null && !input.isEmpty()) {
            try (OutputStream outputStream = process.getOutputStream()) {
                outputStream.write(input.getBytes());
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();

        // Check exit code and handle errors
        if (exitCode != 0) {
            output.append("Execution failed\n");
        }

        return new CompilationResult(output.toString());
    }

    private static void writeStringToFile(String content, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    private String executeCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);

        // Capture error stream
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errorOutput = new StringBuilder();
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            errorOutput.append(errorLine).append("\n");
        }

        // Wait for the process to finish
        process.waitFor();

        return errorOutput.toString();
    }
}
