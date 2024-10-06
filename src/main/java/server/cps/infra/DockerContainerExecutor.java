package server.cps.infra;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.StreamType;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.sun.jna.LastErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.cps.compile.dto.Command;
import server.cps.compile.dto.CompilationResult;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.submission.dto.SubmissionRequstDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
//
@Component
public class DockerContainerExecutor implements ProcessExecutor {
    private final DockerClient dockerClient;
    private CompilationResult compilationResult;

    @Autowired
    public DockerContainerExecutor(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public CompilationResult executeCompile(File file) throws LastErrorException {
        // 컴파일 단계가 필요 없다면 이 메서드는 비워둘 수 있습니다.
        return new CompilationResult("Compilation step skipped.", true);
    }

    @Override
    public CompilationResult executeRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException, LastErrorException {
        return runContainer(compileRequestDTO);
    }

    // 이미지 빌드가 아닌, 파일을 마운트하고 컨테이너를 실행하는 방식으로 수정
    private CompilationResult runContainer(CompileRequestDTO compileRequestDTO) throws InterruptedException {
        CompilationResult compilationResult;
        String language = compileRequestDTO.getLanguage();
        String runCommand = compileRequestDTO.getCommand().getRunCommand();

        File sourceFile = compileRequestDTO.getCodeFile();
        File inputFile = compileRequestDTO.getInputFile();

        String imageName = getDockerImageForLanguage(language);

        if (imageName.isEmpty()) {
            return new CompilationResult("지원하지 않는 언어입니다.", false);
        }

        // 컨테이너 내에서 소스 및 입력 파일의 경로 설정
        String containerSourcePath = "/app/"+ sourceFile.getName();
        String containerInputPath = inputFile != null ? "/app/"+inputFile.getName(): null;

        // Binds 리스트 생성
        List<Bind> binds = new ArrayList<>();
        binds.add(new Bind(sourceFile.getAbsolutePath(), new Volume(containerSourcePath)));
        if (inputFile != null) {
            binds.add(new Bind(inputFile.getAbsolutePath(), new Volume(containerInputPath)));
        }


        try {
            // 컨테이너 생성 및 실행
            String containerId = dockerClient.createContainerCmd(imageName)
                    .withBinds(binds.toArray(new Bind[0]))
                    .withCmd("timeout", "3s","sh", "-c" , runCommand )
                    .exec()
                    .getId();

            dockerClient.startContainerCmd(containerId).exec();

            // 실행 결과 수집
            StringBuilder stdOutResult = new StringBuilder();
            StringBuilder stdErrResult = new StringBuilder();
            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)
                    .exec(new LogContainerResultCallback() {
                        @Override
                        public void onNext(Frame item) {
                            if (item.getStreamType() == StreamType.STDOUT) {
                                stdOutResult.append(preprocessLog(item.toString()));
                            } else if (item.getStreamType() == StreamType.STDERR) {
                                stdErrResult.append(preprocessErrLog(item.toString()));
                            }
                        }
                    })
                    .awaitCompletion(30, TimeUnit.SECONDS);

            // 실행이 끝난 후 컨테이너 삭제
            dockerClient.removeContainerCmd(containerId).exec();

            // 결과 준비
            compilationResult = new CompilationResult(stdOutResult.toString().trim(), true);
            compilationResult.setRunTime(stdErrResult.toString());
            return compilationResult;

        } catch (Exception e) {
            return new CompilationResult("실행 중 오류: " + e.getMessage(), false);
        }
    }


    @Override
    public List<CompilationResult> executeRuns(SubmissionRequstDTO problemRequestDTO) throws IOException, InterruptedException {
        List<CompilationResult> compilationResults = new ArrayList<>();
        try {
            for (int i = 1; i <= problemRequestDTO.getNumberOfFile(); i++) {
                String runCommand = problemRequestDTO.getCommand().getRunCommand() + " < " + i + ".in";

                // CompileRequestDTO 생성
                CompileRequestDTO compileRequestDTO = new CompileRequestDTO();
                Command command = new Command();
                compileRequestDTO.setCommand(command);
                compileRequestDTO.getCommand().setRunCommand(runCommand);
                compileRequestDTO.setLanguage(problemRequestDTO.getLanguage()); // 예를 들어 'python'
                compileRequestDTO.setInputFile(problemRequestDTO.getInputs().get(i-1));
                compileRequestDTO.setCodeFile(problemRequestDTO.getCodeFile());


                // 여러 번 실행하는 코드
                compilationResults.add(runContainer(compileRequestDTO));
            }
        } catch (StringIndexOutOfBoundsException e) {
            CompilationResult cr = new CompilationResult(e.toString(), false);
            compilationResults.add(cr);
        }
        return compilationResults;

    }

    // 언어별 Docker 이미지 선택
    private String getDockerImageForLanguage(String language) {
        switch (language.toLowerCase()) {
            case "py":
                return "python:time";
            case "java":
                return "openjdk:time";
            case "js":
                return "node:time";
            case "c":
                return "gcc:time"; // C 컴파일러가 포함된 Docker 이미지
            case "cpp":
                return "gcc:time";
            default:
                return "";
        }
    }

    private static String preprocessLog(String logEntry) {
        // 특정 문자열이 포함되어 있으면 빈 문자열로 대체
        return logEntry.replace("STDOUT: ", "\n");
    }

    private static String preprocessErrLog(String logEntry) {
        // 특정 문자열이 포함되어 있으면 빈 문자열로 대체
        return logEntry.replace("STDERR:", "");
    }
}

