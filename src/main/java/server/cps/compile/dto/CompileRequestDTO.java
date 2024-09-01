package server.cps.compile.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
public class CompileRequestDTO {
    private String code;
    private String language;
    private String input;
    private Command command;
    private String userName;
    private File codeFile;
    private File inputFile;
    private String folderPath;
    public CompileRequestDTO(String code, String language, String input) {
        this.code = code;
        this.language = language;
        this.input = input;
    }


}

