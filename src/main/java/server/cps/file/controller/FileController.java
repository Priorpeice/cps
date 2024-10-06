package server.cps.file.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.cps.file.mapper.ResourceMapper;
import server.cps.file.service.FileService;
import server.cps.file.vo.FileVO;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class FileController {

    private final FileService fileService;
    private final ResourceMapper resourceMapper;

    @PostMapping("/api/fileDownload/")
    public ResponseEntity<Resource> downloadCodeFile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody  FileVO fileVO)
    {
        fileService.saveFile(fileVO, userDetails.getUsername());
        Resource resource = fileService.loadFile(userDetails.getUsername(),fileVO.getExtension());

        // Content-Disposition 헤더 설정 , fileName은 userName
        HttpHeaders headers = new HttpHeaders();
        String contentDisposition = "attachment; filename=\"" + userDetails.getUsername() + "." + fileVO.getExtension() + "\"";
        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");


        // 성공 응답 반환 (Status.SUCCESS 및 파일 자원)
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
