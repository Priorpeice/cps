package server.cps.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompliationRequest {
    private String code;
    private String language;
    private String input;
}
