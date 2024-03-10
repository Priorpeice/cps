package server.cps.respository;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
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
private final String path= "./user/";
    @Override
    public void codeSave(String code, String userName, String language) {
        try {
            makeFile(code , generateFileName(userName, language),userName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void inputSave(String input,String userName) {
        try {
            makeFile(input , generateFileName(userName, "in"),userName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List readFilesFromFolder(String problemId) {
        return readFilesFromFolder(problemId,"out");
    }

    private void makeFile(String content, String fileName,String userName) throws IOException {
        String userFolderPath = createAndGetFolder(userName);
        String filePath = userFolderPath + "/" + fileName;
        Files.write(Paths.get(filePath), Collections.singleton(content), StandardCharsets.UTF_8);
    }
    private String generateFileName(String userName,String fileExtension) {
        return userName+"."+fileExtension;
    }

    public List<String> readFilesFromFolder(String problemNumber, String fileExtension)  {
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

    @Override
    public int countFile(String problemId ,String extension) {
        String folderPath = "testData/" + problemId + "/";
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(extension));
        return (files != null) ? files.length : 0;
    }
    @Override
    public String getFolder(String userName) {
        return createAndGetFolder(userName);
    }
    private String createAndGetFolder(String userName){
        String userFolderPath = path + userName;
        // 폴더 생성 또는 존재 여부 확인
        File folder = new File(userFolderPath);
        // 폴더 생성
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return userFolderPath;
    }

    private List<String> readInputsFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().collect(Collectors.toList());
        }
    }


}
