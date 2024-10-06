package server.cps.file.mapper;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ResourceMapper {
    /**
     *
     *
     * @param resource 변환할 Resource 객체
     * @return InputStreamResource
     * @throws IOException 파일이 존재하지 않거나 I/O 에러 발생 시
     */
    public InputStreamResource toInputStreamResource(Resource resource) throws IOException {
        if (resource instanceof UrlResource) {
            InputStream inputStream = resource.getInputStream();
            return new InputStreamResource(inputStream);
        } else {
            throw new IOException("Unsupported Resource type: " + resource.getClass().getName());
        }
    }
}
