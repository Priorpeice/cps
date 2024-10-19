package server.cps.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.cps.auth.dto.LoginRequestDTO;
import server.cps.auth.service.LoginService;
import server.cps.auth.service.RegisterService;
import server.cps.auth.service.TokenService;
import server.cps.entity.Member;
import server.cps.member.dto.MemberRequestDTO;
import server.cps.security.TokenInfo;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    private final LoginService loginService;
    private final RegisterService registerService;
    private final TokenService tokenService;
    @GetMapping("member/duplicate")
    public boolean duplicateIdTest(@RequestParam("loginId") String id)
    {
        return registerService.checkDuplicateId(id);
    }
    @PostMapping("member")
    public Member signUp(@RequestBody MemberRequestDTO memberRequestDTO)throws SQLIntegrityConstraintViolationException
    {
        return registerService.signUp(memberRequestDTO);
    }
    @PostMapping("login")
    public TokenInfo login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String memberId = loginRequestDTO.getMemberId();
        String password = loginRequestDTO.getPassword();
        TokenInfo tokenInfo = loginService.login(memberId, password);

        return tokenInfo;
    }
    @PostMapping("/rt")//토큰 재발급 요청
    private TokenInfo reToken(@RequestHeader("refreshToken") String refreshToken,
                              @RequestBody Map<String, Long> id ) {
        Long memberId=id.get("id");

        TokenInfo tokenInfo = loginService.reissue(String.valueOf(memberId),refreshToken);
        System.out.println("tokenInfo.getAccessToken() = " + tokenInfo.getAccessToken());
        System.out.println("tokenInfo.getRefreshToken() = " + tokenInfo.getRefreshToken());
        return tokenInfo;
    }
}
