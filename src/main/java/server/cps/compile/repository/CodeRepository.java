package server.cps.compile.repository;

import java.io.File;
import java.util.List;

public interface CodeRepository<T> {
    void codeSave(String code, String userName, String language);
    void inputSave(String input, String userName);
    List <T> readFilesFromFolder(String problemId) ;
    int countFile(String problemId ,String extension);
    String getFolder(String userName);

    File getCodeFile(String userName ,String language);
    File getInputFile (String userName );
    List<File> getFilesWithExtension(String problemNumber, String extension);
}
