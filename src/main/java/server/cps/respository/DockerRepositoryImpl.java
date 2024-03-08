package server.cps.respository;

import org.springframework.stereotype.Repository;
import server.cps.dto.compile.CompileRequestDTO;

import java.io.File;
import java.io.PrintWriter;
import java.util.Optional;

@Repository
public class DockerRepositoryImpl implements DockerRepository {
    public File generateDockerfile(CompileRequestDTO compileRequestDTO) {
        String projectDirectory = System.getProperty("user.dir");
        File dockerfile = new File(projectDirectory,compileRequestDTO.getUserName());
        System.out.println("compileRequestDTO = " + compileRequestDTO.getLanguage());
        Optional.ofNullable(compileRequestDTO).ifPresent(dto -> {
            try (PrintWriter writer = new PrintWriter(dockerfile)) {
                writer.println("FROM " + dto.getCommand().getImageCommand() + ":latest");
                writer.println("WORKDIR /usr/src/app");
                if(compileRequestDTO.getLanguage().equals("java") ){
                    System.out.println("compileRequestDTO = " + compileRequestDTO.getLanguage());
                    writer.println("COPY " + dto.getUserName() + dto.getCommand().getFileExtension() + " Main.java");
                }else {
                    // COPY 코드 파일
                    writer.println("COPY " + dto.getUserName() + dto.getCommand().getFileExtension()+ " /usr/src/app/");
                }
                if (!dto.getInput().isEmpty()) {

                    // COPY 입력 파일
                    writer.println("COPY " + dto.getInput() + " *" + dto.getCommand().getInputExtension() + " /usr/src/app/");
                }
                // Compile code if needed (language-specific)
                writer.println(dto.getCommand().getCompileCommand());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return dockerfile;
    }
    private static String getBaseImage(String language) {
        switch (language) {
            case "java":
                return "openjdk";
            case "c++":
            case "c":
                return "gcc";
            case "py":
                return "python";
            case "js":
                return "node";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    // 언어에 따른 파일 확장자 선택
    private static String getFileExtension(String language) {
        switch (language) {
            case "java":
                return ".java";
            case "c++":
            case "c":
                return ".c";
            case "py":
                return ".py";
            case "js":
                return ".js";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    // 언어에 따른 입력 파일 확장자 선택
    private static String getInputFileExtension(String language) {
        switch (language) {
            case "java":
            case "c++":
            case "c":
                return "in";
            case "py":
            case "js":
                return "txt";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    // 언어에 따른 컴파일 명령어 선택
    private static String getCompileCommand(String language, String codeName) {
        switch (language) {
            case "java":
                return "RUN javac " + codeName + getFileExtension(language)+" || exit 1";
            case "c++":
            case "c":
                return "RUN gcc -o " + codeName + " " + codeName + getFileExtension(language)+" || exit 1";
            default:
                return "";
        }
    }
}
