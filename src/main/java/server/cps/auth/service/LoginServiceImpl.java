package server.cps.auth.service;


import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.cps.auth.dao.LoginDAO;
import server.cps.auth.dao.TokenDAO;
import server.cps.entity.Login;
import server.cps.exception.LoginPasswordException;
import server.cps.member.dao.MemberDAO;
import server.cps.redis.Token;
import server.cps.security.TokenInfo;
import server.cps.security.TokenProvider;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{
    private final LoginDAO loginDAO;
    private final MemberDAO memberDAO;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider TokenProvider;
    private final BCryptPasswordEncoder encoder;
    private final TokenDAO tokenDAO;
    @Override
    public Login findUserBySeq(Long id) {
        return loginDAO.findBySeq(id);
    }

    @Override
    public Login findUserByLoginId(String loginId) {
        return loginDAO.findByLoginId(loginId);
    }

   @Transactional
    @Override
    public TokenInfo login(String loginId, String password) {
        Login login = loginDAO.findByLoginId(loginId);
        if (encoder.matches(password, login.getPw())) {
            String pw=login.getPw();
            TokenInfo tokenInfo= checkToken(loginId,pw);
            tokenDAO.save(login.getId(),tokenInfo.getRefreshToken());
            return tokenInfo;
        } else {
            throw  new LoginPasswordException("no matches pw", 401);
        }
    }
    @Transactional
    @Override
    public TokenInfo reissue(String memberId, String refreshToken){
        Token token=tokenDAO.findById(memberId);
        if (TokenProvider.validateToken(refreshToken) &&
                refreshToken.equals( token.getRefreshToken()))
        {
            Login login=loginDAO.findByLoginId(memberId);
            TokenInfo tokenInfo = checkToken(login.getId(), login.getPw());
            tokenDAO.save(String.valueOf(login.getSeq()),tokenInfo.getRefreshToken());
            return tokenInfo;
        }
        throw new IllegalArgumentException("올바르지 않은 토큰");
    }

    @Override
    public Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60*3600*24);
        return cookie;
    }

    private TokenInfo checkToken(String loginId, String password) {

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return TokenProvider.generateToken(authentication);
    }



}
