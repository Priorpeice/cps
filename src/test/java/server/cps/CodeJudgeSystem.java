package server.cps;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class CodeJudgeSystem {
    public static void main(String[] args) {
        // 사용자가 제출한 코드
        String userCode = "#include<stdio.h>\n int main(void) {\n  int a;\n  scanf(\"%d\", &a);\n  printf(\"%d\\n hi\", (a+8));\n  return 0;\n}";

        String codeName = "Iam";
        // 테스트 케이스
        int[] testCases = {42, 10, -5};

        // Docker Client 설정
        DockerClientConfig config = createDockerClientConfig();
        DockerHttpClient httpClient = createDockerHttpClient(config);

        BuildImageResultCallback buildCallback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                super.onNext(item);
                // 빌드 로그 출력
                System.out.println(item.getStream());

                // 에러 메시지 출력
                if (item.isErrorIndicated()) {
                    System.err.println("Error during image build: " + item.getErrorDetail().getMessage());
                }
            }
        };


        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        String projectDirectory = System.getProperty("user.dir");
        System.out.println(projectDirectory);
        try {
            File userCodeFile = new File(projectDirectory, codeName+".c");
            try (PrintWriter writer = new PrintWriter(userCodeFile)) {
                writer.println(userCode);
            }
            System.out.println(projectDirectory);
            // Dockerfile 내용을 파일로 저장
            File dockerfile = new File(projectDirectory, "Docker");
            try (PrintWriter writer = new PrintWriter(dockerfile)) {
                writer.println("FROM gcc:latest");
                writer.println("WORKDIR /usr/src/app");
                writer.println("COPY " + codeName+".c /usr/src/app/");
                writer.println("COPY /testData/Q1/*.in /usr/src/app/");
                writer.println("RUN gcc -o "+codeName+" "+codeName+".c || exit 1");
            }

            // 사용자가 제출한 코드를 Docker 이미지로 빌드
            String buildImageId = dockerClient.buildImageCmd()
                    .withDockerfile(dockerfile)
                    .withBaseDirectory(new File(projectDirectory))
                    .exec(buildCallback)
                    .awaitImageId();

            System.out.println("Built Docker image ID: " + buildImageId);

            // 테스트 케이스별로 Docker 컨테이너 실행 및 결과 출력
            for (int i = 0; i < testCases.length; i++) {

                // 입력 파일 생성
                File inputFile = new File(projectDirectory, "input"+i+".in");
                try (FileOutputStream fileOutputStream = new FileOutputStream(inputFile)) {
                    if(i==0) {
                        String str= "qeweqwpe";
                        fileOutputStream.write(str.getBytes());
                    }
                    else {
                        fileOutputStream.write(String.valueOf(testCases[i]).getBytes());
                    }
                }

                // Docker 컨테이너 생성
                String containerId = dockerClient.createContainerCmd(buildImageId)
                        .withCmd("sh", "-c", "cat "+i+".in | ./"+codeName)
                        .exec()
                        .getId();

                // Docker 컨테이너 시작
                dockerClient.startContainerCmd(containerId).exec();

                // Docker 컨테이너 실행 결과 출력
                StringBuilder containerLogs = new StringBuilder();
                dockerClient.logContainerCmd(containerId)
                        .withStdOut(true)
                        .withStdErr(true)
                        .withFollowStream(true)
                        .exec(new LogContainerResultCallback() {
                            @Override
                            public void onNext(Frame item) { String frameContent = item.toString();
                                containerLogs.append(frameContent);

                                // 'ls' 명령어 실행 결과만 출력
                                if (frameContent.contains("ls")) {
                                    System.out.println("ls Result:\n" + frameContent);
                                }

                            }
                        })
                        .awaitCompletion(30, TimeUnit.SECONDS);

                // 컨테이너 로그 출력
                System.out.println("Container Logs:\n" + containerLogs);
                // 컨테이너 제거
                dockerClient.removeContainerCmd(containerId).exec();
            }

        } catch (IOException | InterruptedException e) {
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
    private static String preprocessLog(String logEntry) {
        // 특정 문자열이 포함되어 있으면 빈 문자열로 대체
        return logEntry.replace("STDOUT:", "\n");
    }
}
