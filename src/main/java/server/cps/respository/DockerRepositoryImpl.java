package server.cps.respository;

import org.springframework.stereotype.Repository;
import server.cps.dto.compile.CompileRequestDTO;
import server.cps.dto.problem.ProblemRequstDTO;

import java.io.File;
import java.io.PrintWriter;
import java.util.Optional;

@Repository
public class DockerRepositoryImpl implements DockerRepository {
    @Override
    public <T> File compileDockerfile(T t) {
        if (t instanceof CompileRequestDTO) {
            return compileCompileRequest((CompileRequestDTO) t);
        } else if (t instanceof ProblemRequstDTO) {
            return compileProblemRequest((ProblemRequstDTO) t);
        }
        return  null;
    }

    private File compileCompileRequest(CompileRequestDTO compileRequestDTO) {
        String projectDirectory = System.getProperty("user.dir");
        File dockerfile = new File(projectDirectory,compileRequestDTO.getUserName());
        String userFolderPath=compileRequestDTO.getFolderPath();

        Optional.ofNullable(compileRequestDTO).ifPresent(dto -> {
            try (PrintWriter writer = new PrintWriter(dockerfile)) {
                writer.println("FROM " + dto.getCommand().getImageCommand());
                writer.println("WORKDIR /usr/src/app");

                if (compileRequestDTO.getLanguage().equals("java")) {
                    writer.println("COPY " + userFolderPath + "/" +dto.getUserName()+dto.getCommand().getFileExtension() + " Main.java");
                } else {
                    writer.println("COPY " + userFolderPath + "/" +dto.getUserName()+ dto.getCommand().getFileExtension() + " /usr/src/app/");
                }
                if (!dto.getInput().isEmpty()) {
                    writer.println("COPY " + userFolderPath + "/" + dto.getUserName() + dto.getCommand().getInputExtension() + " /usr/src/app/");
                }

               writer.println(dto.getCommand().getCompileCommand());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return dockerfile;
    }
    private File compileProblemRequest(ProblemRequstDTO problemRequstDTO) {
        String projectDirectory = System.getProperty("user.dir");
        File dockerfile = new File(projectDirectory,problemRequstDTO.getUserName());
        String userFolderPath= problemRequstDTO.getFolderPath();
        Optional.ofNullable(problemRequstDTO).ifPresent(dto -> {
            try (PrintWriter writer = new PrintWriter(dockerfile)) {
                writer.println("FROM " + dto.getCommand().getImageCommand() );
                writer.println("WORKDIR /usr/src/app");
                // COPY 코드 파일
                if(problemRequstDTO.getLanguage().equals("java") ){
                    writer.println("COPY " + userFolderPath + "/" +dto.getUserName()+ dto.getCommand().getFileExtension() + " Main.java");
                }else {
                    writer.println("COPY " + userFolderPath + "/" +dto.getUserName()+ dto.getCommand().getFileExtension() + " /usr/src/app/");
                }
                // COPY 입력 파일
                writer.println("COPY testData/" +problemRequstDTO.getProblemId()+ "/*" + dto.getCommand().getInputExtension() + " /usr/src/app/");
                writer.println("RUN apt-get update && apt-get install -y time");
                writer.println(dto.getCommand().getCompileCommand());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return dockerfile;
    }
}
