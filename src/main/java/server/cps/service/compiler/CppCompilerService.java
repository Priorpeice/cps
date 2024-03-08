package server.cps.service.compiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.cps.dto.compile.Command;
import server.cps.dto.compile.CompileRequestDTO;
import server.cps.infra.ProcessExecutor;
import server.cps.model.CompilationResult;
import server.cps.service.CompilerService;
import server.cps.respository.CodeRepository;
import server.cps.respository.DockerRepository;

import java.io.IOException;


@Component("cpp")
public class CppCompilerService implements CompilerService {

    private final ProcessExecutor processExecutor;
    private final CodeRepository codeRepository;
    private final DockerRepository dockerRepository;
    @Autowired
    public CppCompilerService(ProcessExecutor processExecutor, CodeRepository codeRepository, DockerRepository dockerRepository) {
        this.processExecutor = processExecutor;
        this.codeRepository = codeRepository;
        this.dockerRepository = dockerRepository;
    }

    @Override
    public CompilationResult compileAndRun(CompileRequestDTO compileRequestDTO) throws IOException, InterruptedException {
        codeRepository.save(compileRequestDTO);
        compileRequestDTO.setCommand(command("gcc" , ".cpp",".in" , "RUN g++ -o " + compileRequestDTO.getUserName()+" "+ compileRequestDTO.getUserName()+".cpp || exit 1","./"+compileRequestDTO.getUserName()));
        compileRequestDTO.setFile(dockerRepository.generateDockerfile(compileRequestDTO));
        CompilationResult compilationResult = processExecutor.executeCompile(compileRequestDTO);
        if(compilationResult.isCompile()){
            return processExecutor.executeRun(compileRequestDTO);
        }
        return compilationResult;
    }

    private Command command(String imageCommand, String fileExtension, String inputExtension, String compileCommand , String runCommand ){
        Command command =new Command(imageCommand,fileExtension,inputExtension);
        command.setCompileCommand(compileCommand);
        command.setRunCommand(runCommand);
        return command;
    }





}
