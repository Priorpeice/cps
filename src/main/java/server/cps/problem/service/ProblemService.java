package server.cps.problem.service;

import server.cps.entity.Problem;
import server.cps.problem.dto.ProblemRequestDTO;
import server.cps.problem.dto.ProblemSearchResponseDTO;

import java.util.List;

public interface ProblemService {
    Problem saveProblem(ProblemRequestDTO problemRequestDTO);
    Problem findById(Long id);
    Problem updateProblem(Long id, ProblemRequestDTO problemRequestDTO);
    List<Problem> showProblemAll();
    List<Problem> searchProblems(ProblemRequestDTO problemRequestDTO);
    List<ProblemSearchResponseDTO> findIdAndTitle();
}
