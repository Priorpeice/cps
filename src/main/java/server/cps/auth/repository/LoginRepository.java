package server.cps.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.cps.auth.dto.AuthRequestDTO;
import server.cps.entity.Login;

import java.util.Optional;
@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    Login save(Login login);
    Optional<Login> findBySeq(Long id);
    void delete(Login login);
    Optional<Login> findById(String loginId);
    boolean existsById(String loginId);
    @Query("SELECT new server.cps.auth.dto.AuthRequestDTO(l.id, l.pw, r.userRole) FROM Login l JOIN l.member m JOIN m.role r WHERE l.id = :loginId")
    Optional<AuthRequestDTO> findAuthById(@Param("loginId") String loginId);


}
