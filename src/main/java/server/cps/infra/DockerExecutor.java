package server.cps.infra;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.DockerClientException;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.StreamType;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.problem.dto.ProblemRequstDTO;
import server.cps.model.CompilationResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class DockerExecutor implements ProcessExecutor{
    private final DockerClient dockerClient;
    private CompilationResult compilationResult;
    @Autowired
    public DockerExecutor(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public CompilationResult executeCompile(File file) throws IOException, InterruptedException {
        return buildImage(file);
    }
    @Override
    public CompilationResult executeRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        return runContainer(compilationResult,compileRequestDTO.getCommand().getRunCommand());
    }
    //image build
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
    // container 실행
    private CompilationResult runContainer(CompilationResult compilationResult,String runCommand ) throws InterruptedException {
        CompilationResult finalCompilationResult;
        try {
            String containerId = dockerClient.createContainerCmd(compilationResult.getOutput())
                    .withCmd("timeout", "3s", "sh", "-c", runCommand)
                    .exec()
                    .getId();

            // Docker 컨테이너 시작
            dockerClient.startContainerCmd(containerId).exec();

            // Docker 컨테이너 실행 결과 출력
            StringBuilder stdOutResult = new StringBuilder();
            StringBuilder stdErrResult = new StringBuilder();
            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)
                    .exec(new LogContainerResultCallback() {
                        @Override
                        public void onNext(Frame item) {
                            String frameContent = item.toString();

                            if (item.getStreamType() == StreamType.STDOUT) {
                                stdOutResult.append(preprocessLog(frameContent));
                            } else if (item.getStreamType() == StreamType.STDERR) {
                                stdErrResult.append(preprocessErrLog(frameContent));
                            }
                        }
                    })
                    .awaitCompletion(30, TimeUnit.SECONDS);

            // 컨테이너 로그 출력
            System.out.println("Container Logs:\n" + stdOutResult);
            // 컨테이너 제거
            System.out.println("Standard Error:\n" + stdErrResult);
            CompletableFuture<Void> containerRemovalFuture = CompletableFuture.runAsync(() -> {
                // 컨테이너 삭제
                dockerClient.removeContainerCmd(containerId).exec();
            });

// 컨테이너 삭제가 완료되면 이미지 삭제 실행
            containerRemovalFuture.thenRun(() -> {
                // 이미지 삭제
                dockerClient.removeImageCmd(compilationResult.getOutput()).exec();
            });

            stdOutResult.deleteCharAt(0);
            finalCompilationResult = new CompilationResult(stdOutResult.toString(), true);
            finalCompilationResult.setRunTime(stdErrResult.toString());
        }catch (StringIndexOutOfBoundsException e){
            return new CompilationResult("Wrong Input",false);
        }
        return finalCompilationResult;
    }
    // 테스트 케이스 만큼 실행
    @Override
    public List<CompilationResult> executeRuns(ProblemRequstDTO problemRequstDTO) throws IOException, InterruptedException {
        List<CompilationResult> compilationResults = new ArrayList<>();
        try{
        for (int i = 1; i <= problemRequstDTO.getNumberOfFile(); i++) {
            String runCommand = problemRequstDTO.getCommand().getRunCommand() + " < " + i + ".in";
            // 여러 번 실행하는 코드
            String containerId = dockerClient.createContainerCmd(compilationResult.getOutput())
                    .withCmd("timeout", "3s", "sh", "-c", runCommand)
                    .exec()
                    .getId();

            dockerClient.startContainerCmd(containerId).exec();

            // Docker 컨테이너 실행 결과 출력
            StringBuilder stdOutResult = new StringBuilder();
            StringBuilder stdErrResult = new StringBuilder();
            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)
                    .exec(new LogContainerResultCallback() {
                        @Override
                        public void onNext(Frame item) {
                            String frameContent = item.toString();
                            System.out.println(frameContent);
                            if (item.getStreamType() == StreamType.STDOUT) {
                                stdOutResult.append(preprocessLog(frameContent));
                            } else if (item.getStreamType() == StreamType.STDERR) {
                                stdErrResult.append(preprocessErrLog(frameContent));
                            }
                        }
                    })
                    .awaitCompletion(30, TimeUnit.SECONDS);

            stdOutResult.deleteCharAt(0);
            dockerClient.removeContainerCmd(containerId).exec();
            CompilationResult cr = new CompilationResult(stdOutResult.toString(), true);
            cr.setRunTime(stdErrResult.toString());
            compilationResults.add(cr);
        }
    } catch (StringIndexOutOfBoundsException e){
            CompilationResult cr= new CompilationResult(e.toString(),false);
            compilationResults.add(cr);
        }
        return compilationResults;
    }

    private static String preprocessLog(String logEntry) {
        // 특정 문자열이 포함되어 있으면 빈 문자열로 대체
        return logEntry.replace("STDOUT:", "\n");
    }
    private static String preprocessErrLog(String logEntry) {
        // 특정 문자열이 포함되어 있으면 빈 문자열로 대체
        return logEntry.replace("STDERR:", "");
    }


}

