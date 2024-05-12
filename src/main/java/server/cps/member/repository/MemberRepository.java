package server.cps.member.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.cps.entity.Member;

import java.util.List;
import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member save(Member member);
    @EntityGraph(attributePaths = {"login", "role"})
    Optional<Member> findById(Long id);
    List<Member> findAll();
    void delete(Member member);

    @Query("SELECT m FROM Member m JOIN FETCH m.login JOIN FETCH m.role WHERE m.login.id = :loginId")
    Optional<Member> findByLoginIdWithMember(@Param("loginId") String loginId);

}
