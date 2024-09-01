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

@Component("c")
public class CCompilerService implements CompilerService  {

    private final ProcessExecutor processExecutor;
    private final CodeRepository codeRepository;
    private final DockerRepository dockerRepository;
    @Autowired
    public CCompilerService(ProcessExecutor processExecutor, CodeRepository codeRepository ,DockerRepository dockerRepository) {
        this.processExecutor =processExecutor;
        this.codeRepository= codeRepository;
        this.dockerRepository= dockerRepository;
    }
    @Override
    public CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        compileRequestDTO.setFolderPath(codeRepository.getFolder(compileRequestDTO.getUserName()));
        codeRepository.codeSave(compileRequestDTO.getCode(),compileRequestDTO.getUserName(),compileRequestDTO.getLanguage());
        compileRequestDTO.setCodeFile(codeRepository.getCodeFile(compileRequestDTO.getUserName(),compileRequestDTO.getLanguage()));
        compileRequestDTO.setCommand(command("gcc:latest" , ".c",".in" , "RUN gcc -o " + compileRequestDTO.getUserName()+" "+ compileRequestDTO.getUserName()+".c || exit 1","gcc -o "+"/app/"+compileRequestDTO.getUserName()+" /app/"+compileRequestDTO.getUserName()+".c && time -p /app/"+compileRequestDTO.getUserName() ));
        if(!compileRequestDTO.getInput().isEmpty()){
            codeRepository.inputSave(compileRequestDTO.getInput(),compileRequestDTO.getUserName());
            compileRequestDTO.setInputFile(codeRepository.getInputFile(compileRequestDTO.getUserName()));
            compileRequestDTO.getCommand().setRunCommand("gcc -o "+"/app/"+compileRequestDTO.getUserName()+" /app/"+compileRequestDTO.getUserName()+".c && time -p /app/"+compileRequestDTO.getUserName()+" < "+compileRequestDTO.getUserName()+".in");
        }
        CompilationResult compilationResult = processExecutor.executeRun(compileRequestDTO);

        return compilationResult;
    }

    @Override
    public List<CompilationResult> testAndRun(SubmissionRequstDTO problemRequstDTO) throws IOException,InterruptedException{
        problemRequstDTO.setFolderPath(codeRepository.getFolder(problemRequstDTO.getUserName()));
        codeRepository.codeSave(problemRequstDTO.getCode(),problemRequstDTO.getUserName(),problemRequstDTO.getLanguage());
        problemRequstDTO.setCodeFile(codeRepository.getCodeFile(problemRequstDTO.getUserName(),problemRequstDTO.getLanguage()));
        problemRequstDTO.setCommand(command("gcc:latest" , ".c",".in" , "RUN gcc -o " + problemRequstDTO.getUserName()+" "+ problemRequstDTO.getUserName()+".c || exit 1","gcc -o "+"/app/"+problemRequstDTO.getUserName()+" /app/"+problemRequstDTO.getUserName()+".c && time -p /app/"+problemRequstDTO.getUserName() ));
        problemRequstDTO.setNumberOfFile(codeRepository.countFile(problemRequstDTO.getProblemId(), ".in"));
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
