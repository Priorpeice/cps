package server.cps.service.compiler;

import org.springframework.stereotype.Component;
import server.cps.infra.DockerExecutor;
import server.cps.model.CompilationResult;
import server.cps.model.Compiler;

import java.io.File;
import java.io.IOException;

@Component("Test")
public class TestCompiler implements Compiler {
    DockerExecutor dockerExecutor;

    public TestCompiler(DockerExecutor dockerExecutor) {
        this.dockerExecutor = dockerExecutor;
    }
    public CompilationResult compile(File docker){
        return dockerExecutor.buildImage(docker);
    }
    public CompilationResult run(CompilationResult compilationResult,String codeName,String input,String language){
        try {
            return dockerExecutor.runContainer(compilationResult,codeName,input,language);
        } catch (InterruptedException e) {
           return new CompilationResult(e.toString(),false);
        }
    }

    @Override
    public CompilationResult compile(String code, String input) throws IOException, InterruptedException {
        return null;
    }

    @Override
    public CompilationResult run(String code, String input) throws IOException, InterruptedException {
        return null;
    }
}
