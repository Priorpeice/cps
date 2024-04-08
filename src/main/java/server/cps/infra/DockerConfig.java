package server.cps.infra;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DockerConfig {
    @Bean
    @Scope("singleton")
    public DockerClient dockerClient() {
        DockerClientConfig config = createDockerClientConfig();
        DockerHttpClient httpClient = createDockerHttpClient(config);
        return DockerClientImpl.getInstance(config, httpClient);
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
