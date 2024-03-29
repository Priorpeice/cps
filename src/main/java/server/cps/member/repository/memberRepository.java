package server.cps.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.cps.entity.Member;

public interface memberRepository extends JpaRepository<Member,Long> {
    Member save(Member member);
}
