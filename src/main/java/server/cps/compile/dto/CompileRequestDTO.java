package server.cps.compile.dto;

import lombok.Getter;
import lombok.Setter;
import server.cps.compile.dto.Command;

import java.io.File;

@Getter
@Setter
public class CompileRequestDTO {
    private String code;
    private String language;
    private String input;
    private Command command;
    private String userName;
    private File file;
    private String folderPath;
    public CompileRequestDTO(String code, String language, String input) {
        this.code = code;
        this.language = language;
        this.input = input;
    }

}

