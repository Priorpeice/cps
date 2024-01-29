package server.cps.respository;

import java.io.IOException;
import java.util.List;

public interface FileRepository {
    public void writeStringToFile(String content, String fileName) throws IOException;
    public String generateFileName(String fileExtension);
    public List<String> readInputsFromFiles(String problemNumber) throws IOException;

}
