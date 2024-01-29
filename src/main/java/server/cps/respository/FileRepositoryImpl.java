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
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FileRepositoryImpl implements FileRepository{
    @Override
    public void writeStringToFile(String content, String fileName) throws IOException {
        Files.write(Paths.get(fileName), Collections.singleton(content), StandardCharsets.UTF_8);
    }
    @Override
    public String generateFileName(String fileExtension) {
        return "Main." + fileExtension;
    }
    public List<String> readInputsFromFiles(String problemNumber) throws IOException {
        String folderPath = "test/" + problemNumber + "/";
        List<String> inputs = new ArrayList<>();

        Files.walk(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        inputs.addAll(readInputsFromFile(filePath.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return inputs;
    }

    private List<String> readInputsFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

}
