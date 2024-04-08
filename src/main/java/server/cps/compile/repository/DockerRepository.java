package server.cps.compile.repository;

import java.io.File;

public interface DockerRepository {
    <T> File compileDockerfile(T t);
}
