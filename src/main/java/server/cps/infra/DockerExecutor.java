package server.cps.infra;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.cps.model.CompilationResult;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Component
public class DockerExecutor {
    private final DockerClient dockerClient;
    private CompilationResult compilationResult;
    @Autowired
    public DockerExecutor(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }
    public CompilationResult buildImage(File dockerfile) {
        String projectDirectory = System.getProperty("user.dir");
        // 코드와 입력값을 파일로 저장
        BuildImageResultCallback buildCallback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                super.onNext(item);
                // 빌드 로그 출력
//                System.out.println(item.getStream());

                // 에러 메시지 출력
                if (item.isErrorIndicated()) {
                    System.err.println("Error during image build: " + item.getErrorDetail().getMessage());
                    compilationResult = new CompilationResult( item.getErrorDetail().getMessage(),false);
                }
            }
        };

        String buildImageId = dockerClient.buildImageCmd()
                .withDockerfile(dockerfile)
                .withBaseDirectory(new File(projectDirectory))
                .exec(buildCallback)
                .awaitImageId();

        System.out.println("Built Docker image ID: " + buildImageId);
        if (compilationResult == null) {
            compilationResult = new CompilationResult(buildImageId, true);
        }
        return compilationResult;

    }
    public CompilationResult runContainer(CompilationResult compilationResult,String codeName,String input, String language) throws InterruptedException {

        String containerId = dockerClient.createContainerCmd(compilationResult.getOutput())
                .withCmd("sh", "-c", getExecutionCommand(language, codeName, input))
                .exec()
                .getId();
        // Docker 컨테이너 시작
        dockerClient.startContainerCmd(containerId).exec();

        // Docker 컨테이너 실행 결과 출력
        StringBuilder result = new StringBuilder();
        dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .withFollowStream(true)
                .exec(new LogContainerResultCallback() {
                    @Override
                    public void onNext(Frame item) {
                        String frameContent = item.toString();
                        result.append(frameContent);

                        // 'ls' 명령어 실행 결과만 출력
                        if (frameContent.contains("ls")) {
                            System.out.println("ls Result:\n" + frameContent);
                        }

                    }
                })
                .awaitCompletion(30, TimeUnit.SECONDS);

        // 컨테이너 로그 출력
        System.out.println("Container Logs:\n" + result);
        // 컨테이너 제거
        dockerClient.removeContainerCmd(containerId).exec();
        return new CompilationResult(result.toString(),true);
    }
    private String getExecutionCommand(String language, String codeName, String input) {
        switch (language) {
            case "java":
                return "java "+"echo \"" + input + "\" | ./"+codeName;
            case "c++":
            case "c":
                return "echo \"" + input + "\" | ./"+codeName;
            case "python":
                return "python " + codeName+".py";
            default:
                return "";
        }
    }
}

