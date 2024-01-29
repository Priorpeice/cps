package server.cps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.cps.infra.ProcessExecutor;
import server.cps.infra.ProcessExecutorImpl;
import server.cps.respository.FileRepository;
import server.cps.respository.FileRepositoryImpl;
import server.cps.service.*;
import server.cps.model.Compiler;


import java.util.HashMap;
import java.util.Map;


@Configuration
public class AppConfig {
    @Bean
    public Compiler cCompiler(@Autowired ProcessExecutor processExecutor) {
        return new CCompiler(processExecutor);
    }

    @Bean
    public Compiler pythonRunner(@Autowired ProcessExecutor processExecutor) {
        return new PythonRunner(processExecutor);
    }

    @Bean
    public Compiler javaCompiler(@Autowired ProcessExecutor processExecutor) {
        return new JavaCompiler(processExecutor);
    }

    @Bean
    public Compiler cppCompiler(@Autowired ProcessExecutor processExecutor) {
        return new CppCompiler(processExecutor);
    }
    @Bean
    public Map<String, Compiler> languageCompilerMap(@Qualifier("c") Compiler cCompiler,
                                                     @Qualifier("py") Compiler pythonCompiler,
                                                     @Qualifier("java") Compiler javaCompiler,
                                                     @Qualifier("cpp") Compiler cppCompiler) {
        Map<String, Compiler> languageCompilerMap = new HashMap<>();
        languageCompilerMap.put("c", cCompiler);
        languageCompilerMap.put("py", pythonCompiler);
        languageCompilerMap.put("java", javaCompiler);
        languageCompilerMap.put("cpp",cppCompiler);
        return languageCompilerMap;
    }

    @Bean
    public CompileService CompileService(@Autowired Map<String, Compiler> languageCompilerMap,
                                         @Autowired FileRepository fileRepository) {
        return new CompileService(languageCompilerMap, fileRepository);
    }

    @Bean
    public FileRepository fileRepository() {
        return new FileRepositoryImpl();
    }

    @Bean
    public ProcessExecutor processExecutor(){return new ProcessExecutorImpl();}

}
