package server.cps.compile.compiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.cps.compile.dto.Command;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.submission.dto.SubmissionRequstDTO;
import server.cps.infra.ProcessExecutor;
import server.cps.compile.dto.CompilationResult;
import server.cps.compile.repository.CodeRepository;
import server.cps.compile.repository.DockerRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("java")
public class JavaCompilerService implements CompilerService {
    private final ProcessExecutor processExecutor;
    private final CodeRepository codeRepository;
    private final DockerRepository dockerRepository;
    @Autowired
    public JavaCompilerService(ProcessExecutor processExecutor, CodeRepository codeRepository, DockerRepository dockerRepository) {
        this.processExecutor = processExecutor;
        this.codeRepository = codeRepository;
        this.dockerRepository = dockerRepository;
    }

    @Override
    public CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        compileRequestDTO.setFolderPath(codeRepository.getFolder(compileRequestDTO.getUserName()));
        codeRepository.codeSave(compileRequestDTO.getCode(),compileRequestDTO.getUserName(),compileRequestDTO.getLanguage());
        compileRequestDTO.setCommand(command("openjdk:latest" , ".java",".in" , "RUN javac Main.java || exit 1","java Main"));
        if(!compileRequestDTO.getInput().isEmpty()){
            codeRepository.inputSave(compileRequestDTO.getInput(),compileRequestDTO.getUserName());
            compileRequestDTO.getCommand().setRunCommand("java Main"+"<"+compileRequestDTO.getUserName()+".in");
        }
        compileRequestDTO.setFile(dockerRepository.compileDockerfile(compileRequestDTO));
        CompilationResult compilationResult = processExecutor.executeCompile(compileRequestDTO.getFile());
        if(compilationResult.isCompile()){
            return processExecutor.executeRun(compileRequestDTO);
        }
        return compilationResult;
    }

    @Override
    public List<CompilationResult> testAndRun(SubmissionRequstDTO problemRequstDTO) throws InterruptedException, IOException {
        problemRequstDTO.setFolderPath(codeRepository.getFolder(problemRequstDTO.getUserName()));
        codeRepository.codeSave(problemRequstDTO.getCode(),problemRequstDTO.getUserName(),problemRequstDTO.getLanguage());
        problemRequstDTO.setCommand(command("openjdk:23-slim-bullseye" , ".java",".in" , "RUN javac Main.java || exit 1","time -p java Main"));
        problemRequstDTO.setNumberOfFile(codeRepository.countFile(problemRequstDTO.getProblemId(), ".in"));
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

