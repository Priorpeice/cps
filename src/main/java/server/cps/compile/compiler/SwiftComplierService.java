package server.cps.compile.compiler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.cps.compile.dto.Command;
import server.cps.compile.dto.CompilationResult;
import server.cps.compile.dto.CompileRequestDTO;
import server.cps.compile.repository.CodeRepository;
import server.cps.infra.ProcessExecutor;
import server.cps.submission.dto.SubmissionRequstDTO;

import java.io.IOException;
import java.util.List;
@Component("swift")
@RequiredArgsConstructor

public class SwiftComplierService implements CompilerService {
    private final ProcessExecutor processExecutor;
    private final CodeRepository codeRepository;
    @Override
    public CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        compileRequestDTO.setFolderPath(codeRepository.getFolder(compileRequestDTO.getUserName()));
        codeRepository.codeSave(compileRequestDTO.getCode(), compileRequestDTO.getUserName(), compileRequestDTO.getLanguage());
        compileRequestDTO.setCodeFile(codeRepository.getCodeFile(compileRequestDTO.getUserName(), compileRequestDTO.getLanguage()));
        compileRequestDTO.setCommand(command("", ".swift", ".in", "", "time -p swift " + "/app/" + compileRequestDTO.getUserName() + ".swift"));

        if (!compileRequestDTO.getInput().isEmpty()) {
            codeRepository.inputSave(compileRequestDTO.getInput(), compileRequestDTO.getUserName());
            compileRequestDTO.setInputFile(codeRepository.getInputFile(compileRequestDTO.getUserName()));
            compileRequestDTO.getCommand().setRunCommand("swift " + compileRequestDTO.getUserName() + ".swift < " + compileRequestDTO.getUserName() + ".in");
        }

        CompilationResult compilationResult = processExecutor.executeRun(compileRequestDTO);

        return compilationResult;
    }

    @Override
    public List<CompilationResult> testAndRun(SubmissionRequstDTO problemRequstDTO) throws InterruptedException, IOException {
        problemRequstDTO.setFolderPath(codeRepository.getFolder(problemRequstDTO.getUserName()));
        codeRepository.codeSave(problemRequstDTO.getCode(), problemRequstDTO.getUserName(), problemRequstDTO.getLanguage());
        problemRequstDTO.setCodeFile(codeRepository.getCodeFile(problemRequstDTO.getUserName(), problemRequstDTO.getLanguage()));
        problemRequstDTO.setCommand(command("", ".swift", ".in", "", "time -p swift " + problemRequstDTO.getUserName() + ".swift"));
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
