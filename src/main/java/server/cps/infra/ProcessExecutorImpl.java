package server.cps.infra;

import org.springframework.stereotype.Component;
import server.cps.model.CompilationResult;

import java.io.*;

@Component
public class ProcessExecutorImpl implements ProcessExecutor {
    public  CompilationResult executeCommand(String[] command, String input) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        if (input != null && !input.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(input);
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
