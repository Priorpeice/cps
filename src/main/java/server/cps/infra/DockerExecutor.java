package server.cps.infra;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.DockerClientException;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.cps.dto.compile.CompileRequestDTO;
import server.cps.model.CompilationResult;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class DockerExecutor implements ProcessExecutor{
    private final DockerClient dockerClient;
    private CompilationResult compilationResult;
    @Autowired
    public DockerExecutor(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }
    private CompilationResult buildImage(File dockerfile) {
        StringBuilder log = new StringBuilder();
        String projectDirectory = System.getProperty("user.dir");
        BuildImageResultCallback buildCallback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                super.onNext(item);
                // 빌드 로그 출력
                System.out.println(item.getStream());
                log.append(item.getStream());

                // 에러 메시지 출력
                if (item.isErrorIndicated()) {
                    String errorMessage = item.getErrorDetail().getMessage();
                    System.err.println("Error during image build: " + errorMessage);
                    compilationResult = new CompilationResult(log.toString(), false);
                    }
                }
        };
        try {
            String buildImageId = dockerClient.buildImageCmd()
                    .withDockerfile(dockerfile)
                    .withBaseDirectory(new File(projectDirectory))
                    .exec(buildCallback)
                    .awaitImageId();

            System.out.println("Built Docker image ID: " + buildImageId);
//        if (compilationResult == null) {
            compilationResult = new CompilationResult(buildImageId, true);
//        }
            return compilationResult;
        }
        catch (DockerClientException e){
            return compilationResult;
        }
    }
    private CompilationResult runContainer(CompilationResult compilationResult,String runCommand ,String userName) throws InterruptedException {

        String containerId = dockerClient.createContainerCmd(compilationResult.getOutput())
                .withCmd("sh","-c",runCommand)
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
                        result.append(preprocessLog(frameContent));
                    }
                })
                .awaitCompletion(30, TimeUnit.SECONDS);

        // 컨테이너 로그 출력
        System.out.println("Container Logs:\n" + result);
        // 컨테이너 제거
        dockerClient.removeContainerCmd(containerId).exec();
        result.deleteCharAt(0);
        return new CompilationResult(result.toString(),true);
    }
    private String getExecutionCommand(String language, String codeName, String input) {
        switch (language) {
            case "java":
                return "java "+codeName;
            case "c++":
            case "c":
                return "./"+codeName;
            case "py":
                return "python3 " + codeName + ".py";
            case "js":
                return "node "+codeName +".js";
            default:
                return "";
        }
    }

    @Override
    public CompilationResult executeCompile(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        return buildImage(compileRequestDTO.getFile());
    }
    @Override
    public CompilationResult executeRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        return runContainer(compilationResult,compileRequestDTO.getCommand().getRunCommand(), compileRequestDTO.getUserName());
    }
    private static String preprocessLog(String logEntry) {
        // 특정 문자열이 포함되어 있으면 빈 문자열로 대체
        return logEntry.replace("STDOUT:", "\n");
    }
}

