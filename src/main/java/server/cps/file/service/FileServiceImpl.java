package server.cps.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import server.cps.file.vo.FileVO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    /*properties로 빼기*/
    private final String defaultPath= "./user/";
    @Override
    public void saveFile(FileVO fileVO, String path)
    {
        try {

            String directoryPath = defaultPath + path;
            Path targetDirectory = Paths.get(directoryPath).toAbsolutePath().normalize();

            // 디렉토리가 없으면 생성
            Files.createDirectories(targetDirectory);

            // 파일 경로 설정
            Path filePath = targetDirectory.resolve(path + "." + fileVO.getExtension());

            // 파일 저장
            File file = new File(filePath.toString());
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(fileVO.getContent());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*ErrorException 빼기*/
    @Override
    public Resource loadFile(String fileName, String extension) {
        try {
            // 폴더 구조 user/"userName"/"userName.Extension"
            String directoryPath = defaultPath + fileName;

            // 기본 경로에서 파일을 찾음
            Path filePath = Paths.get(directoryPath).resolve(fileName + '.' + extension).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // 파일이 존재하는지 확인
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + fileName, e);
        }
    }
}
