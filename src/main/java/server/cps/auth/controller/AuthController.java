package server.cps.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.cps.auth.dto.LoginRequestDTO;
import server.cps.auth.service.LoginService;
import server.cps.security.TokenInfo;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final LoginService loginService;


    @PostMapping("/api/login")
    public TokenInfo login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String memberId = loginRequestDTO.getMemberId();
        String password = loginRequestDTO.getPassword();
        TokenInfo tokenInfo = loginService.login(memberId, password);
        return tokenInfo;
    }
}
