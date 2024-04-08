package server.cps.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.cps.entity.Member;

import java.util.List;
import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member save(Member member);
    Optional<Member> findById(Long id);
    List<Member> findAll();
    void delete(Member member);

}
