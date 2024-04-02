package server.cps.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import server.cps.auth.dao.LoginDAO;
import server.cps.entity.Login;
import server.cps.entity.Member;
import server.cps.entity.Role;
import server.cps.member.dao.MemberDAO;
import server.cps.member.dto.MemberRequestDTO;
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
    @Override
    public Login findUserBySeq(Long id) {
        return loginDAO.findBySeq(id);
    }

    @Override
    public Login findUserByLoginId(String loginId) {
        return loginDAO.findByLoginId(loginId);
    }

    @Override
    public Login save(Login login)
    {
        return loginDAO.save(login);
    }
    @Override
    public Member signUp(@RequestBody MemberRequestDTO memberRequestDTO)throws SQLIntegrityConstraintViolationException
    {
        //Memeber toEntity
        String encode = encoder.encode(memberRequestDTO.getPw());
        memberRequestDTO.setPw(encode);
        Member member =memberRequestDTO.toEntity();
        member.setUser(memberRequestDTO.toEntity(member));

        member.setRole(Role.builder()
                .member(member)
                .build());
        //save
        return memberDAO.save(member);
    }


    @Transactional
    @Override
    public TokenInfo login(String memberId, String password) {
        Login login = loginDAO.findByLoginId(memberId);
        if (encoder.matches(password,login.getPw())== true) {

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = TokenProvider.generateToken(authentication);
            return tokenInfo;
        } else {
            throw  new RuntimeException();
        }

    }

}
