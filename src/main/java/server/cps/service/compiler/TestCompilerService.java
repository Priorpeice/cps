package server.cps.service.compiler;

import org.springframework.stereotype.Component;
import server.cps.infra.DockerExecutor;
import server.cps.model.CompilationResult;

@Component("Test")
public class TestCompilerService  {
    DockerExecutor dockerExecutor;

    public TestCompilerService(DockerExecutor dockerExecutor) {
        this.dockerExecutor = dockerExecutor;
    }
//    public CompilationResult compile(File docker){
//        return dockerExecutor.executeCompile(docker);
//    }
    public CompilationResult run(CompilationResult compilationResult,String codeName,String input,String language){
//        try {
////            return dockerExecutor.runContainer(compilationResult,codeName,input,language);
//        } catch (InterruptedException e) {
//           return new CompilationResult(e.toString(),false);
//        }
        return null;
    }



}
