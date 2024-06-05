package server.cps.example.repository;

import org.springframework.stereotype.Repository;
import server.cps.entity.Example;
import server.cps.entity.Problem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
@Repository
public class FileExampleRepositoryImpl implements FileExampleRepository {

    @Override
    public void makeInputAndOutputFile(Problem problem) {
        String folderPath = "testData/" + problem.getId() + "/";
        List<Example> examples = problem.getExamples();

        File folder = new File(folderPath);
        // 폴더가 없을 경우에만 생성
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (int i = 0; i < examples.size(); i++) {
            Example example = examples.get(i);
            String input = example.getInput();
            String output = example.getOutput();

            // 입력 파일 생성
            String inputFilePath = folderPath + (i + 1) + ".in";
            createFile(inputFilePath, input);

            // 출력 파일 생성
            String outputFilePath = folderPath + (i + 1) + ".out";
            createFile(outputFilePath, output);
        }
    }

    private void createFile(String filePath, String content) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
