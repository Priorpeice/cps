package server.cps.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.cps.auth.dto.LoginRequestDTO;
import server.cps.auth.service.LoginService;
import server.cps.auth.service.RegisterService;
import server.cps.auth.service.TokenService;
import server.cps.common.CpsResponse;
import server.cps.common.ResponseBody;
import server.cps.common.Status;
import server.cps.entity.Member;
import server.cps.member.dto.MemberRequestDTO;
import server.cps.security.TokenInfo;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
    @PostMapping("/member")
    public Member signUp(@RequestBody MemberRequestDTO memberRequestDTO)throws SQLIntegrityConstraintViolationException
    {
        return registerService.signUp(memberRequestDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseBody<TokenInfo>> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        String memberId = loginRequestDTO.getMemberId();
        String password = loginRequestDTO.getPassword();
        TokenInfo tokenInfo = loginService.login(memberId, password);
        Cookie cookie = loginService.createCookie(tokenInfo.getRefreshToken());
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        response.addCookie(cookie);

        return CpsResponse.toResponse(Status.SUCCESS,tokenInfo, HttpStatus.OK.value());
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
