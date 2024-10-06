package server.cps.compile.compiler;

import org.springframework.stereotype.Component;
import server.cps.compile.dto.Command;
import server.cps.compile.dto.CompilationResult;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.compile.repository.CodeRepository;
import server.cps.compile.repository.DockerRepository;
import server.cps.infra.ProcessExecutor;
import server.cps.submission.dto.SubmissionRequstDTO;

import java.io.IOException;
import java.util.List;

@Component("py")
public class PythonRunnerService implements CompilerService {
    private final ProcessExecutor processExecutor;
    private final CodeRepository codeRepository;
    private final DockerRepository dockerRepository;

    public PythonRunnerService(ProcessExecutor processExecutor, CodeRepository codeRepository, DockerRepository dockerRepository) {
        this.processExecutor = processExecutor;
        this.codeRepository = codeRepository;
        this.dockerRepository = dockerRepository;
    }

    @Override
    public CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        compileRequestDTO.setFolderPath(codeRepository.getFolder(compileRequestDTO.getUserName()));
        codeRepository.codeSave(compileRequestDTO.getCode(),compileRequestDTO.getUserName(),compileRequestDTO.getLanguage());
        compileRequestDTO.setCodeFile(codeRepository.getCodeFile(compileRequestDTO.getUserName(),compileRequestDTO.getLanguage()));
        compileRequestDTO.setCommand(command("python:latest" , ".py",".in" , "","time -p python3 "+"/app/"+compileRequestDTO.getUserName()+".py"));
        if(!compileRequestDTO.getInput().isEmpty()){
            codeRepository.inputSave(compileRequestDTO.getInput(),compileRequestDTO.getUserName());
            compileRequestDTO.setInputFile(codeRepository.getInputFile(compileRequestDTO.getUserName()));
            compileRequestDTO.getCommand().setRunCommand("time -p python3 "+ "/app/"+compileRequestDTO.getCodeFile().getName() +" < "+ "/app/"+compileRequestDTO.getInputFile().getName());
        }

        CompilationResult compilationResult = processExecutor.executeRun(compileRequestDTO);

        return compilationResult;
    }

    @Override
    public List<CompilationResult> testAndRun(SubmissionRequstDTO problemRequstDTO) throws InterruptedException, IOException {
        problemRequstDTO.setFolderPath(codeRepository.getFolder(problemRequstDTO.getUserName()));
        codeRepository.codeSave(problemRequstDTO.getCode(),problemRequstDTO.getUserName(),problemRequstDTO.getLanguage());
        problemRequstDTO.setCodeFile(codeRepository.getCodeFile(problemRequstDTO.getUserName(),problemRequstDTO.getLanguage()));
        problemRequstDTO.setCommand(command("python:latest" , ".py",".in" , "","time -p python3 "+problemRequstDTO.getUserName()+".py"));
        problemRequstDTO.setInputs(codeRepository.getFilesWithExtension(problemRequstDTO.getProblemId(), ".in"));
        List<CompilationResult> compilationResults = processExecutor.executeRuns(problemRequstDTO);

        return compilationResults;
    }

    private Command command(String imageCommand, String fileExtension, String inputExtension, String compileCommand , String runCommand ){
        Command command =new Command(imageCommand,fileExtension,inputExtension);
        command.setCompileCommand(compileCommand);
        command.setRunCommand(runCommand);
        return command;
    }

}