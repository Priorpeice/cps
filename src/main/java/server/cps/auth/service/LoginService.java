package server.cps.auth.service;

import jakarta.servlet.http.Cookie;
import server.cps.entity.Login;
import server.cps.security.TokenInfo;

public interface LoginService {
    Login findUserBySeq(Long id);
    Login findUserByLoginId(String loginId);

    TokenInfo login(String memberId, String password);
    TokenInfo reissue(String memberId, String refreshToken);

    Cookie createCookie(String refreshToken);


}
