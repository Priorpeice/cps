package server.cps.service.compiler;

import org.springframework.stereotype.Component;
import server.cps.dto.compile.Command;
import server.cps.dto.compile.CompileRequestDTO;
import server.cps.dto.problem.ProblemRequstDTO;
import server.cps.infra.ProcessExecutor;
import server.cps.model.CompilationResult;
import server.cps.respository.CodeRepository;
import server.cps.respository.DockerRepository;
import server.cps.service.CompilerService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("py")
public class PythonRunner implements CompilerService {
    private final ProcessExecutor processExecutor;
    private final CodeRepository codeRepository;
    private final DockerRepository dockerRepository;

    public PythonRunner(ProcessExecutor processExecutor, CodeRepository codeRepository, DockerRepository dockerRepository) {
        this.processExecutor = processExecutor;
        this.codeRepository = codeRepository;
        this.dockerRepository = dockerRepository;
    }

    @Override
    public CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        compileRequestDTO.setFolderPath(codeRepository.getFolder(compileRequestDTO.getUserName()));
        codeRepository.codeSave(compileRequestDTO.getCode(),compileRequestDTO.getUserName(),compileRequestDTO.getLanguage());
        compileRequestDTO.setCommand(command("python:latest" , ".py",".in" , "","time -p python3 "+compileRequestDTO.getUserName()+".py"));
        if(!compileRequestDTO.getInput().isEmpty()){
            codeRepository.inputSave(compileRequestDTO.getInput(),compileRequestDTO.getUserName());
            compileRequestDTO.getCommand().setRunCommand("python3 "+compileRequestDTO.getUserName()+".py"+"<"+compileRequestDTO.getUserName()+".in");
        }
        compileRequestDTO.setFile(dockerRepository.compileDockerfile(compileRequestDTO));
        CompilationResult compilationResult = processExecutor.executeCompile(compileRequestDTO.getFile());
        if(compilationResult.isCompile()){
            return processExecutor.executeRun(compileRequestDTO);
        }
        return compilationResult;
    }

    @Override
    public List<CompilationResult> testAndRun(ProblemRequstDTO problemRequstDTO) throws InterruptedException, IOException {
        problemRequstDTO.setFolderPath(codeRepository.getFolder(problemRequstDTO.getUserName()));
        codeRepository.codeSave(problemRequstDTO.getCode(),problemRequstDTO.getUserName(),problemRequstDTO.getLanguage());
        problemRequstDTO.setCommand(command("python:latest" , ".py",".in" , "","python3 "+problemRequstDTO.getUserName()+".py"));
        File file =dockerRepository.compileDockerfile(problemRequstDTO);
        CompilationResult compilationResult = processExecutor.executeCompile(file);
        List<CompilationResult> compilationResults = new ArrayList<>();
        if(compilationResult.isCompile()) {
            compilationResults = processExecutor.executeRuns(problemRequstDTO);
        }else{
            compilationResults.add(compilationResult);
        }
        return compilationResults;
    }

    private Command command(String imageCommand, String fileExtension, String inputExtension, String compileCommand , String runCommand ){
        Command command =new Command(imageCommand,fileExtension,inputExtension);
        command.setCompileCommand(compileCommand);
        command.setRunCommand(runCommand);
        return command;
    }

}