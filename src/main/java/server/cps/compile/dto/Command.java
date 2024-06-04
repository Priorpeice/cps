package server.cps.compile.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Command {
    private String imageCommand;
    private String fileExtension;
    private String inputExtension;


    private String compileCommand;
    private String runCommand;


    public Command(String imageCommand, String fileExtension, String inputExtension) {
        this.imageCommand = imageCommand;
        this.fileExtension = fileExtension;
        this.inputExtension = inputExtension;
    }

    public Command(String imageCommand, String fileExtension, String inputExtension, String compileCommand, String runCommand) {
        this.imageCommand = imageCommand;
        this.fileExtension = fileExtension;
        this.inputExtension = inputExtension;
        this.compileCommand = compileCommand;
        this.runCommand = runCommand;
    }
}
