package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service("python")
public class PythonRunner implements Compiler {
    @Override
    public CompilationResult compile(String code, String input) throws IOException, InterruptedException {
        String fileName = "temp.py";
        writeStringToFile(code, fileName);
     return new CompilationResult(fileName,true);
    }
    @Override
    public CompilationResult run(String fileName, String input) throws IOException, InterruptedException {
        List<String> command = Arrays.asList("python", fileName);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        // 입력 처리
        if (input != null && !input.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(input);
                writer.flush();
            }
        }
        // 출력 처리
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // 프로세스의 종료를 기다림
            int exitCode = process.waitFor();

            // 종료 코드 확인 및 오류 처리
            if (exitCode != 0) {
                return new CompilationResult(output.toString(),false);
            }

            return new CompilationResult(output.toString(),true);
        }
    }
    private static void writeStringToFile(String content, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }


}