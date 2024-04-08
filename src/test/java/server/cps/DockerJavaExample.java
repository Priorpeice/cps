package server.cps;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.AttachContainerResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.io.*;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class DockerJavaExample {
    public static void main(String[] args) {
        // Docker 클라이언트 설정
        DockerClientConfig config = createDockerClientConfig();
        DockerHttpClient httpClient = createDockerHttpClient(config);

        // 이미지 빌드
        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        String projectDirectory = System.getProperty("user.dir");
        String dockerfilePath = Paths.get(projectDirectory, "Dockerfile").toString();
        String imageId = dockerClient.buildImageCmd()
                .withDockerfile(new File(dockerfilePath))
                .withBaseDirectory(new File(projectDirectory))
                .exec(new BuildImageResultCallback())
                .awaitImageId();

        // 사용자 입력 값
        String userInput = "777";

        // 컨테이너 생성
        CreateContainerResponse containerResponse = dockerClient.createContainerCmd(imageId)
                .withCmd("./usercode")  // 수정: 컴파일된 실행 파일의 이름을 기존의 "myprogram"에서 "usercode"로 변경
                .exec();

        String containerId = containerResponse.getId();

        // 컨테이너 시작
        dockerClient.startContainerCmd(containerId).exec();

        // Docker 컨테이너에 stdin 연결
        try (
                PipedOutputStream out = new PipedOutputStream();
                PipedInputStream in = new PipedInputStream(out)
        ) {
            dockerClient.attachContainerCmd(containerId)
                    .withStdIn(in)
                    .exec(new AttachContainerResultCallback())
                    .awaitCompletion();

            // 사용자 입력을 stdin으로 전송
            out.write(userInput.getBytes());
            out.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // stdin 닫기
        try {
            dockerClient.attachContainerCmd(containerId)
                    .withStdIn(new ByteArrayInputStream(new byte[0]))
                    .exec(new ResultCallback.Adapter<>())
                    .awaitCompletion();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 컨테이너 상태 확인
        dockerClient.waitContainerCmd(containerId)
                .exec(new WaitContainerResultCallback())
                .awaitStatusCode();

        // 컨테이너 로그 확인
        StringBuilder containerLogs = new StringBuilder();
        try {
            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .exec(new LogContainerResultCallback() {
                        @Override
                        public void onNext(Frame item) {
                            containerLogs.append(item.toString());
                        }
                    })
                    .awaitCompletion(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 컨테이너 로그 출력
        System.out.println("Container Logs:\n" + containerLogs);
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
