package server.cps.respository;

import server.cps.dto.compile.CompileRequestDTO;

import java.io.File;

public interface DockerRepository {
    public File generateDockerfile(CompileRequestDTO compileRequestDTO);
}
