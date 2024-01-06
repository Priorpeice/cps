package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import java.io.*;
@Service("pythonCompiler")
public class PythonRunner implements Compiler {
    @Override
    public CompilationResult compileAndRun(String code) throws IOException, InterruptedException {
        return compileAndRun(code, null);
    }

    @Override
    public CompilationResult compileAndRun(String code, String input) throws IOException, InterruptedException {
        String fileName = "temp.py";
        writeStringToFile(code, fileName);

        String runCommand = "python " + fileName;

        ProcessBuilder processBuilder = new ProcessBuilder(runCommand);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        if (input != null && !input.isEmpty()) {
            try (OutputStream outputStream = process.getOutputStream()) {
                outputStream.write(input.getBytes());
                outputStream.flush();
            }
        }

        // Read the output of the command
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        // Wait for the process to finish
        process.waitFor();

        return new CompilationResult(output.toString());
    }
    private static void writeStringToFile(String content, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }
    private static String executeCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);

        // Read the output of the command
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }


        process.waitFor();

        return output.toString();
    }

}