package server.cps.member.service;

import server.cps.entity.Member;
import server.cps.member.dto.MemberRequestDTO;

import java.util.List;

public interface MemberSevice {
    Member createMember(MemberRequestDTO memberRequestDTO);
    Member findMember(Long id);
    Member updateMember(MemberRequestDTO memberRequestDTO);
    void deleteMember(Long id);

    List<Member> findAllMember();
}
