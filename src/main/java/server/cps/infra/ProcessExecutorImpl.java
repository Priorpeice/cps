package server.cps.infra;

import server.cps.dto.compile.CompileRequestDTO;
import server.cps.model.CompilationResult;

import java.io.*;


public class ProcessExecutorImpl implements ProcessExecutor {
    public  CompilationResult executeCompile(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        return executeCommand(compileRequestDTO);
    }
    @Override
    public CompilationResult executeRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        return executeCommand(compileRequestDTO);
    }
    private CompilationResult executeCommand(CompileRequestDTO compileRequestDTO) throws  IOException, InterruptedException{
        ProcessBuilder processBuilder = new ProcessBuilder(compileRequestDTO.getCommand().getCompileCommand());
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        if (compileRequestDTO.getInput() != null && !compileRequestDTO.getInput().isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(compileRequestDTO.getInput());
                writer.flush();
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                output.append("fail").append("\n");
                return new CompilationResult(output.toString(),false);
            }

            return new CompilationResult(output.toString(),true);
        }
    }
}
