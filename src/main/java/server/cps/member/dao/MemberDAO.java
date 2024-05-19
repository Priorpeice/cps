package server.cps.member.dao;

import server.cps.entity.Member;

import java.util.List;

public interface MemberDAO {
    Member save(Member member);
    Member findById(Long id);
    List<Member> findAll();
    void delete(Long id);
    Member findByLoginIdWithMember(String loginid);
}
