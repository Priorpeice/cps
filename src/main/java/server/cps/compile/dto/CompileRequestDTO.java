package server.cps.compile.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompileRequestDTO {
    private String code;
    private String language;
    private String input;


    public CompileRequestDTO(String code, String language, String input) {
        this.code = code;
        this.language = language;
        this.input = input;
    }

}

