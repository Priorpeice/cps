package server.cps.problem.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.cps.entity.Problem;
import server.cps.problem.dto.ProblemSearchResponseDTO;

import java.util.List;

public interface ProblemDAO {
    Problem save(Problem problem);
    Problem findById(Long id);
    Page<Problem> findAll(Pageable pageable);
    void deleteById(Long id);
    Page<Problem> search(Pageable pageable,String title);
    List<ProblemSearchResponseDTO> findIdAndTitle();
}
