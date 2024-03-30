package server.cps.problem.dao;

import server.cps.entity.Problem;
import server.cps.problem.dto.ProblemSearchResponseDTO;

import java.util.List;

public interface ProblemDAO {
    Problem save(Problem problem);
    Problem findById(Long id);
    List<Problem> findAll();
    void deleteById(Long id);
    List<Problem> search(String title);
    List<ProblemSearchResponseDTO> findIdAndTitle();
}
