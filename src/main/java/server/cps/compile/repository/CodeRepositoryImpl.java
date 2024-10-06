package server.cps.compile.repository;

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
    @Override
    public List<File> getFilesWithExtension(String problemNumber, String extension) {
        List<File> files = new ArrayList<>();
        String folderPath = "testData/" + problemNumber + "/";

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] fileArray = folder.listFiles((dir, name) -> name.endsWith(extension));
            if (fileArray != null) {
                for (File file : fileArray) {
                    files.add(file);
                }
                Collections.sort(files, Comparator.comparing(File::getName));
            }
        } else {
            System.out.println("not exist: " + folderPath);
        }

        return files;
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

    @Override
    public File getCodeFile(String userName , String language) {
        String codePath = path+userName+"/"+userName;
        return new File(codePath+"."+ language);
    }

    @Override
    public File getInputFile(String userName) {
        String inputPath = path+userName+"/"+userName;
        return new File(inputPath+".in");
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
