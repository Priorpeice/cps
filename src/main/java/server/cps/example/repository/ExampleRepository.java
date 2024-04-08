package server.cps.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.cps.entity.Example;

import java.util.Optional;
@Repository

public interface ExampleRepository extends JpaRepository<Example,Long> {
    Example save(Example example);
    Optional<Example> findById(Long id);
    void delete(Example example);
}
