package server.cps.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.cps.entity.Member;
import server.cps.member.dao.MemberDAO;
import server.cps.member.dto.MemberRequestDTO;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberSevice{
    private final MemberDAO memberDAO;

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

    @Override
    public Member findMemberWithLoginid(String loginid) {
        return memberDAO.findByLoginIdWithMember(loginid);
    }
}
