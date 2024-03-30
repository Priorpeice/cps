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
    public Member createMember(MemberRequestDTO memberRequestDTO) {
        return null;
    }

    @Override
    public Member findMember(Long id) {
        return null;
    }

    @Override
    public Member updateMember(MemberRequestDTO memberRequestDTO) {
        return null;
    }

    @Override
    public void deleteMember(Long id) {

    }

    @Override
    public List<Member> findAllMember() {
        return null;
    }
}
