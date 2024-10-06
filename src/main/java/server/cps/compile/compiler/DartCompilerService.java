package server.cps.compile.compiler;

import lombok.RequiredArgsConstructor;
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


@Component("dart")
@RequiredArgsConstructor
public class DartCompilerService implements CompilerService{
    private final CodeRepository codeRepository;
    private final DockerRepository dockerRepository;
    private final ProcessExecutor processExecutor;

    @Override
    public CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        compileRequestDTO.setFolderPath(codeRepository.getFolder(compileRequestDTO.getUserName()));
        codeRepository.codeSave(compileRequestDTO.getCode(),compileRequestDTO.getUserName(),compileRequestDTO.getLanguage());
        compileRequestDTO.setCodeFile(codeRepository.getCodeFile(compileRequestDTO.getUserName(),compileRequestDTO.getLanguage()));
        compileRequestDTO.setCommand(command("dart:latest AS dart-builder" , ".dart",".in" , "","dart "+compileRequestDTO.getUserName()+".dart"));
        if(!compileRequestDTO.getInput().isEmpty()){
            codeRepository.inputSave(compileRequestDTO.getInput(),compileRequestDTO.getUserName());
            compileRequestDTO.setInputFile(codeRepository.getInputFile(compileRequestDTO.getUserName()));
            compileRequestDTO.getCommand().setRunCommand("dart "+compileRequestDTO.getUserName()+".dart"+"<"+compileRequestDTO.getUserName()+".in");
        }

        CompilationResult compilationResult = processExecutor.executeRun(compileRequestDTO);

        return compilationResult;
    }


    /* Dart 채점 로직 바꿔야함*/

    @Override
    public List<CompilationResult> testAndRun(SubmissionRequstDTO problemRequstDTO) throws InterruptedException, IOException {
        problemRequstDTO.setFolderPath(codeRepository.getFolder(problemRequstDTO.getUserName()));
        codeRepository.codeSave(problemRequstDTO.getCode(),problemRequstDTO.getUserName(),problemRequstDTO.getLanguage());
        problemRequstDTO.setCodeFile(codeRepository.getCodeFile(problemRequstDTO.getUserName(),problemRequstDTO.getLanguage()));
        problemRequstDTO.setCommand(command("dart:latest AS dart-builder" , ".dart",".in" , "","dart "+problemRequstDTO.getUserName()+".dart"));
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
