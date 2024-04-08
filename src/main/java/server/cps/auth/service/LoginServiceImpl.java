package server.cps.auth.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import server.cps.auth.dao.LoginDAO;
import server.cps.auth.dao.TokenDAO;
import server.cps.entity.Login;
import server.cps.entity.Member;
import server.cps.entity.Role;
import server.cps.member.dao.MemberDAO;
import server.cps.member.dto.MemberRequestDTO;
import server.cps.redis.Token;
import server.cps.security.TokenInfo;
import server.cps.security.TokenProvider;

import java.sql.SQLIntegrityConstraintViolationException;

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

    @Override
    public Member signUp(@RequestBody MemberRequestDTO memberRequestDTO)throws SQLIntegrityConstraintViolationException
    {
        //Memeber toEntity
        String encode = encoder.encode(memberRequestDTO.getPw());
        memberRequestDTO.setPw(encode);
        Member member =memberRequestDTO.toEntity();
        member.setLogin(memberRequestDTO.toEntity(member));

        member.setRole(Role.builder()
                .member(member)
                .build());
        //save
        return memberDAO.save(member);
    }

   @Transactional
    @Override
    public TokenInfo login(String loginId, String password) {
        Login login = loginDAO.findByLoginId(loginId);
        if (encoder.matches(password, login.getPw())) {
            String pw=login.getPw();
            TokenInfo tokenInfo= checkToken(loginId,pw);
            tokenDAO.save(String.valueOf(login.getSeq()),tokenInfo.getRefreshToken());
            return tokenInfo;
        } else {
            throw  new IllegalArgumentException();
        }
    }
    @Transactional
    @Override
    public TokenInfo reissue(String memberId, String refreshToken){
        Token token=tokenDAO.findById(memberId);
        if (TokenProvider.validateToken(refreshToken) &&
                refreshToken.equals( token.getRefreshToken()))
        {
            Login login=loginDAO.findBySeq(Long.valueOf(memberId));
            TokenInfo tokenInfo = checkToken(login.getId(), login.getPw());
            tokenDAO.save(String.valueOf(login.getSeq()),tokenInfo.getRefreshToken());
            return tokenInfo;
        }
        throw new IllegalArgumentException("올바르지 않은 토큰");
    }
    private TokenInfo checkToken(String loginId, String password) {

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return TokenProvider.generateToken(authentication);
    }



}
