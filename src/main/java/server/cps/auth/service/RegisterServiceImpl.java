package server.cps.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import server.cps.auth.dao.LoginDAO;
import server.cps.entity.Member;
import server.cps.entity.Role;
import server.cps.member.dao.MemberDAO;
import server.cps.member.dto.MemberRequestDTO;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService{
    private final MemberDAO memberDAO;
    private final BCryptPasswordEncoder encoder;
    private final LoginDAO loginDAO;

    @Override
    public Member signUp(@RequestBody MemberRequestDTO memberRequestDTO)throws SQLIntegrityConstraintViolationException
    {
        //Memeber toEntity
        String encode = encoder.encode(memberRequestDTO.getPw());
        memberRequestDTO.setPw(encode);
        Member member = memberRequestDTO.toEntity();
        member.setLogin(memberRequestDTO.toEntity(member));

        member.setRole(Role.builder()
                .member(member)
                .build());
        //save
        return memberDAO.save(member);
    }

    @Override
    public boolean checkDuplicateId(String id) {
        boolean checkId =false;
        if(!isValidUsername(id))
        {
            return checkId;
        }
        checkId = loginDAO.checkDuplicateId(id);
        return checkId;
    }

    private boolean isValidUsername(String id) {

        return id.matches("^[a-zA-Z0-9]{1,15}$");
    }
}
