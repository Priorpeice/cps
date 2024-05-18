package server.cps.problem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.cps.entity.Problem;
import server.cps.problem.dto.ProblemSearchResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProblemRespository extends JpaRepository<Problem,Long> {
    Problem save(Problem problem);
    Optional<Problem> findById(Long id);
    List<Problem> findAll();
    @Query("SELECT new server.cps.problem.dto.ProblemSearchResponseDTO(p.id,p.title) FROM Problem p")
    List<ProblemSearchResponseDTO> findIdAndTitle();
    void deleteById(Long id);
    void delete(Problem problem);
    Page<Problem> findAll(Pageable pageable);
    Page<Problem> findByTitleContainingIgnoreCase(Pageable pageable,String title);

}
