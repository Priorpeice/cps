package server.cps.respository;

import java.io.File;

public interface DockerRepository {
    public File generateDockerfile(String language, String codeName, String inputPath, String fileName);
}
