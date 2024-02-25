package server.cps.respository;

import java.io.IOException;
import java.util.List;

public interface CodeRepository {
    public void writeStringToFile(String content, String fileName) throws IOException;
    public String generateFileName(String fileExtension);
    public List<String> readFilesFromFolder(String problemNumber, String fileExtension) throws IOException;
}
