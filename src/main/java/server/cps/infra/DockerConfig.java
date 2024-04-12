package server.cps.infra;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.sun.jna.LastErrorException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import server.cps.exception.DockerException;

@Configuration
public class DockerConfig {
    @Bean
    @Scope("singleton")
    public DockerClient dockerClient()  {
        try {
            DockerClientConfig config = createDockerClientConfig();
            DockerHttpClient httpClient = createDockerHttpClient(config);
            return DockerClientImpl.getInstance(config, httpClient);
        }
        catch (LastErrorException e){
             throw new DockerException(e.toString(),500);
        }
    }
    private static DockerClientConfig createDockerClientConfig() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("unix:///var/run/docker.sock")
                .build();
    }

    private static DockerHttpClient createDockerHttpClient(DockerClientConfig config) {
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .build();
    }
}
