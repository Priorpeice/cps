package server.cps.problem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.cps.entity.Problem;
import server.cps.problem.dto.ProblemRequestDTO;
import server.cps.problem.dto.ProblemSearchResponseDTO;

import java.util.List;

public interface ProblemService {
    Problem saveProblem(ProblemRequestDTO problemRequestDTO);
    Problem findById(Long id);
    Problem updateProblem(Long id, ProblemRequestDTO problemRequestDTO);
    Page<Problem> showProblemAll(Pageable pageable);
    Page<Problem> searchProblems(Pageable pageable,ProblemRequestDTO problemRequestDTO);
    List<ProblemSearchResponseDTO> findIdAndTitle();

}
