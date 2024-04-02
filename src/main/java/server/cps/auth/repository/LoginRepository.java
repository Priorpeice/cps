package server.cps.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.cps.entity.Login;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login save(Login login);
    Optional<Login> findBySeq(Long id);
    void delete(Login login);
    Optional<Login> findById(String loginId);

}
