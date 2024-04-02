package server.cps.auth.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String memberId;
    private String password;
}
