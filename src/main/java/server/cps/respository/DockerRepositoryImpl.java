package server.cps.respository;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.PrintWriter;
@Repository
public class DockerRepositoryImpl implements DockerRepository {
    public File generateDockerfile(String language, String codeName, String input , String dockerfileName) {
        String projectDirectory = System.getProperty("user.dir");


        File dockerfile = new File(projectDirectory, dockerfileName);

        try (PrintWriter writer = new PrintWriter(dockerfile)) {
            writer.println("FROM " + getBaseImage(language) + ":latest");
            writer.println("WORKDIR /usr/src/app");

            // COPY 코드 파일
            writer.println("COPY " + codeName + getFileExtension(language) + " /usr/src/app/");

            if(input != null) {
                // COPY 입력 파일
                writer.println("COPY " + input + "*." + getInputFileExtension(language) + " /usr/src/app/");
            }
            // Compile code if needed (language-specific)
            writer.println(getCompileCommand(language, codeName));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dockerfile;
    }
    private static String getBaseImage(String language) {
        switch (language) {
            case "java":
                return "openjdk";
            case "c++":
            case "c":
                return "gcc";
            case "python":
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
            case "python":
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
            case "python":
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
                return "RUN javac " + codeName + getFileExtension(language) + " || exit 1";
            case "c++":
            case "c":
                return "RUN gcc -o " + codeName + " " + codeName + getFileExtension(language) + " || exit 1";
            default:
                return "";
        }
    }
}
