package server.cps.auth.dto;

import lombok.Data;
import server.cps.entity.userRole.UserRole;
@Data

public class AuthRequestDTO {
    private String id;
    private String pw;
    private String userRole;

    public AuthRequestDTO(String id, String pw, UserRole userRole) {
        this.id = id;
        this.pw = pw;
        this.userRole = userRole.name(); // UserRole 열거형을 문자열로 변환하여 저장
    }
}
