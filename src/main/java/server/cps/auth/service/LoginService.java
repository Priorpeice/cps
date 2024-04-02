package server.cps.auth.service;

import org.springframework.web.bind.annotation.RequestBody;
import server.cps.entity.Login;
import server.cps.entity.Member;
import server.cps.member.dto.MemberRequestDTO;
import server.cps.security.TokenInfo;

import java.sql.SQLIntegrityConstraintViolationException;

public interface LoginService {
    Login findUserBySeq(Long id);
    Login findUserByLoginId(String loginId);
    Login save(Login login);
    TokenInfo login(String memberId, String password);
    Member signUp(@RequestBody MemberRequestDTO memberRequestDTO)throws SQLIntegrityConstraintViolationException;
}
