package server.cps.compile.compiler;

import org.springframework.beans.factory.annotation.Autowired;
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
        compileRequestDTO.setCodeFile(codeRepository.getCodeFile(compileRequestDTO.getUserName(),compileRequestDTO.getLanguage()));
        compileRequestDTO.setCommand(command("openjdk:latest" , ".java",".in" , "RUN javac Main.java || exit 1","cp /app/"+compileRequestDTO.getUserName()+".java /app/Main.java && javac /app/Main.java && time -p java Main"));
        if(!compileRequestDTO.getInput().isEmpty()){
            codeRepository.inputSave(compileRequestDTO.getInput(),compileRequestDTO.getUserName());
            compileRequestDTO.setInputFile(codeRepository.getInputFile(compileRequestDTO.getUserName()));
            compileRequestDTO.getCommand().setRunCommand("cp /app/"+compileRequestDTO.getUserName()+".java /app/Main.java && javac /app/Main.java && time -p java Main"+"<"+compileRequestDTO.getUserName()+".in");
        }

        CompilationResult compilationResult = processExecutor.executeRun(compileRequestDTO);

        return compilationResult;
    }

    @Override
    public List<CompilationResult> testAndRun(SubmissionRequstDTO problemRequstDTO) throws InterruptedException, IOException {
        problemRequstDTO.setFolderPath(codeRepository.getFolder(problemRequstDTO.getUserName()));
        codeRepository.codeSave(problemRequstDTO.getCode(),problemRequstDTO.getUserName(),problemRequstDTO.getLanguage());
        problemRequstDTO.setCodeFile(codeRepository.getCodeFile(problemRequstDTO.getUserName(),problemRequstDTO.getLanguage()));
        problemRequstDTO.setCommand(command("openjdk:23-slim-bullseye" , ".java",".in" , "RUN javac Main.java || exit 1","cp /app/"+problemRequstDTO.getUserName()+".java /app/Main.java && javac Main.java && time -p java Main"));
        problemRequstDTO.setInputs(codeRepository.getFilesWithExtension(problemRequstDTO.getProblemId(), ".in"));
        problemRequstDTO.setNumberOfFile(codeRepository.countFile(problemRequstDTO.getProblemId(), ".in"));
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

