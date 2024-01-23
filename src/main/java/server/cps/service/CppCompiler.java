package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import java.io.*;

@Service("cpp")
public class CppCompiler implements Compiler {
    @Override
    public CompilationResult compile(String code, String input) throws IOException, InterruptedException {
        String fileName = "temp.cpp";
        writeStringToFile(code, fileName);

        String compileCommand = "g++ " + fileName + " -o temp";
        String compileOutput = executeCommand(compileCommand, input,true);

        if (compileOutput.contains("error")) {
            return new CompilationResult("Compilation failed:\n" + compileOutput, false);
        }

        return new CompilationResult("temp", true); // fileName
    }

    @Override
    public CompilationResult run(String fileName, String input) throws IOException, InterruptedException {
        String runCommand = "./" + fileName;

        String runOutput = executeCommand(runCommand, input,false);

        return new CompilationResult(runOutput, true);
    }

    private static String executeCommand(String command, String input, boolean useErrorStream ) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);

        if (input != null && !input.isEmpty()) {
            try (OutputStream outputStream = process.getOutputStream()) {
                outputStream.write(input.getBytes());
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(useErrorStream ? process.getErrorStream() : process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        process.waitFor();

        return output.toString();
    }

    private static void writeStringToFile(String content, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }
}
