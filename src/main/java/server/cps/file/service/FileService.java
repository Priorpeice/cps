package server.cps.file.service;

import org.springframework.core.io.Resource;
import server.cps.file.vo.FileVO;

public interface FileService {
    void saveFile(FileVO fileVO, String path);
    Resource loadFile(String fileName, String extension);
}
