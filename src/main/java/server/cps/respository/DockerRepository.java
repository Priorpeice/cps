package server.cps.respository;

import java.io.File;

public interface DockerRepository {
    <T> File compileDockerfile(T t);
}
