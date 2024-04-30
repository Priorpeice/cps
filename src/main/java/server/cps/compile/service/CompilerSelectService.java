package server.cps.compile.service;

import com.sun.jna.LastErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.cps.compile.compiler.CompilerService;

import java.util.Map;
@Service
public class CompilerSelectService {
    private final Map<String, CompilerService> languageCompilerMap;
    @Autowired
    public CompilerSelectService(Map<String, CompilerService> languageCompilerMap) {
        this.languageCompilerMap = languageCompilerMap;
    }

    public CompilerService getCompilerForLanguage(String language) throws LastErrorException {
        CompilerService compiler = languageCompilerMap.get(language);
        if (compiler != null) {
            return compiler;
        }
        throw new IllegalArgumentException("Unsupported Language " + language);
    }
}
