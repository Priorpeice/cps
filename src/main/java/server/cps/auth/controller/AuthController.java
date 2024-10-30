package server.cps.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Arrays;
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
    @PostMapping("/rt") // 토큰 재발급 요청
    private ResponseEntity<ResponseBody<TokenInfo>> reToken(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> id) {
        String memberId = id.get("id");

        // HttpServletRequest를 사용하여 쿠키에서 refreshToken을 가져옵니다.
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null) {
            throw new RuntimeException("Refresh token not found");
        }

        TokenInfo tokenInfo = loginService.reissue(memberId, refreshToken);
        Cookie cookie = loginService.createCookie(tokenInfo.getRefreshToken());
        response.addCookie(cookie);
        return CpsResponse.toResponse(Status.SUCCESS,tokenInfo, HttpStatus.OK.value());
    }

}
