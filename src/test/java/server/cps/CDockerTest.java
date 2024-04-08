package server.cps;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class CDockerTest {

    public static void main(String[] args) {
        try {
            // Docker 클라이언트 설정
            DockerClientConfig config = createDockerClientConfig();
            DockerHttpClient httpClient = createDockerHttpClient(config);

            // Docker 클라이언트 생성 (try-with-resources 사용)
            try (DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient)) {
                // 실행할 C 코드
                String cCode = "#include<stdio.h>\n int main(void) {\n  int a;\n int b;\n  scanf(\"%d %d\", &a, &b);\n  printf(\"%d %d\\n\", a, b);\n  return 0;\n}";

                // C 코드를 파일로 저장 (프로젝트의 src 디렉토리에 저장)
                String projectDirectory = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리
                String dockerfilePath = Paths.get(projectDirectory, "Dockerfile").toString();
                String cCodeFilePath = Paths.get(projectDirectory, "myprogram.c").toString();

                String userInput = "131 123";
                String userInputFilePath = Paths.get(projectDirectory, "input.txt").toString();
                Files.write(Paths.get(userInputFilePath), userInput.getBytes());

                Files.write(Paths.get(cCodeFilePath), cCode.getBytes());
                // Docker 이미지 빌드
                BuildImageResultCallback buildCallback = new BuildImageResultCallback() {
                    @Override
                    public void onNext(BuildResponseItem item) {
                        super.onNext(item);
                        // 빌드 로그 출력
                        System.out.println(item.getStream());
                    }
                };

                BuildImageCmd buildImageCmd = dockerClient.buildImageCmd()
                        .withDockerfile(new File(dockerfilePath))
                        .withBaseDirectory(new File(projectDirectory))
                        .withTags(Collections.singleton("mygccimage:latest"));


                String imageId = buildImageCmd.exec(buildCallback).awaitImageId();
                System.out.println("Built Docker image ID: " + imageId);

                // Docker 컨테이너 생성
                // Docker 컨테이너 생성
                String containerId = dockerClient.createContainerCmd(imageId)
                        .withCmd("/bin/bash", "-c", "echo \"" + userInput + "\" | ./myprogram")
                        .exec()
                        .getId();



                // Docker 컨테이너 시작
                dockerClient.startContainerCmd(containerId).exec();

                // 컨테이너 상태 확인
                dockerClient.waitContainerCmd(containerId)
                        .exec(new WaitContainerResultCallback())
                        .awaitStatusCode();

                // 컨테이너 로그 확인
                StringBuilder containerLogs = new StringBuilder();
                dockerClient.logContainerCmd(containerId)
                        .withStdOut(true)
                        .withStdErr(true)
                        .withFollowStream(true)
                        .exec(new LogContainerResultCallback() {
                            @Override
                            public void onNext(Frame item) {
                                containerLogs.append(item.toString());
                            }
                        })
                        .awaitCompletion(30, TimeUnit.SECONDS);

                // 컨테이너 로그 출력
                System.out.println("Container Logs:\n" + containerLogs);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
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
