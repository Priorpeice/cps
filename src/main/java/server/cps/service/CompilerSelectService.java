package server.cps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class CompilerSelectService {
    private final Map<String, CompilerService> languageCompilerMap;
    @Autowired
    public CompilerSelectService(Map<String, CompilerService> languageCompilerMap) {
        this.languageCompilerMap = languageCompilerMap;
    }

    public CompilerService getCompilerForLanguage(String language) {
        CompilerService compiler = languageCompilerMap.get(language);
        System.out.println(compiler);
        if (compiler != null) {
            return compiler;
        }
        throw new IllegalArgumentException("Unsupported Language " + language);
    }
}