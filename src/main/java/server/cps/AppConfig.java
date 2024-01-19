package server.cps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.cps.service.IdeService;
import server.cps.model.Compiler;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class AppConfig {
    @Bean
    public Map<String, Compiler> languageCompilerMap(@Qualifier("c") Compiler cCompiler,
                                                     @Qualifier("python") Compiler pythonCompiler,
                                                     @Qualifier("java") Compiler javaCompiler,
                                                     @Qualifier("cpp") Compiler cppCompiler) {
        Map<String, Compiler> languageCompilerMap = new HashMap<>();
        languageCompilerMap.put("c", cCompiler);
        languageCompilerMap.put("python", pythonCompiler);
        languageCompilerMap.put("java", javaCompiler);
        languageCompilerMap.put("cpp",cppCompiler);
        return languageCompilerMap;
    }


    @Bean
    public IdeService ideService( @Autowired Map<String, Compiler> languageCompilerMap) {
        return new IdeService(languageCompilerMap);
    }

}
