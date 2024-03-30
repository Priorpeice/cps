package server.cps.member.dao;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.cps.entity.Member;
import server.cps.member.repository.MemberRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class MemberDAOImpl implements MemberDAO{
    private final MemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        memberRepository.delete(member);
    }
}
