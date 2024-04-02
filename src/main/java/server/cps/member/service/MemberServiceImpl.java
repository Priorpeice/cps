package server.cps.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.cps.entity.Member;
import server.cps.entity.Role;
import server.cps.member.dao.MemberDAO;
import server.cps.member.dto.MemberRequestDTO;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberSevice{
    private final MemberDAO memberDAO;
    private final BCryptPasswordEncoder encoder;
    @Override
    public Member createMember(MemberRequestDTO memberRequestDTO) {
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

    @Override
    public Member findMember(Long id) {
        return memberDAO.findById(id);
    }

    @Override
    public Member updateMember(MemberRequestDTO memberRequestDTO) {
        //find
        //save
        return null;
    }

    @Override
    public void deleteMember(Long id) {
       memberDAO.delete(id);
    }

    @Override
    public List<Member> findAllMember() {
        return memberDAO.findAll();
    }
}
