package server.cps.respository;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CodeRepositoryImpl implements CodeRepository {
    @Override
    public void writeStringToFile(String content, String fileName) throws IOException {
        Files.write(Paths.get(fileName), Collections.singleton(content), StandardCharsets.UTF_8);
    }
    @Override
    public String generateFileName(String fileExtension) {
        return "Main";
    }
    public List<String> readFilesFromFolder(String problemNumber, String fileExtension) throws IOException {
        List<String> fileContents = new ArrayList<>();
        String folderPath = "testData/" + problemNumber + "/";

        try {
            Files.walk(Paths.get(folderPath))
                    .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(fileExtension))
                    .sorted(    Comparator.comparing(path -> Integer.parseInt(path.getFileName().toString().split("\\.")[0])))
                    .forEach(filePath -> {
                        try {
                            List<String> lines = readInputsFromFile(filePath.toString());
                            String content = String.join(System.lineSeparator(), lines);

                            // 파일 경로 출력
                            System.out.println("Read file: " + filePath);

                            fileContents.add(content);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContents;
    }

    private List<String> readInputsFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().collect(Collectors.toList());
        }
    }


}
