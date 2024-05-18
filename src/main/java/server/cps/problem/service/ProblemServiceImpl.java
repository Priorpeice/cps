package server.cps.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.cps.entity.Problem;
import server.cps.problem.dao.ProblemDAO;
import server.cps.problem.dto.ProblemRequestDTO;
import server.cps.problem.dto.ProblemSearchResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemServiceImpl implements ProblemService{
    private final ProblemDAO problemDAO;
    @Override
    public Problem saveProblem(ProblemRequestDTO problemRequestDTO) {
       Problem problem= problemRequestDTO.toEntity();
       problem.setExamples(problemRequestDTO.toEntitys(problem));
       return problemDAO.save(problem);
    }
    @Override
    public Problem findById(Long id) {
        return problemDAO.findById(id);
    }

    @Override
    public Problem updateProblem(Long id, ProblemRequestDTO problemRequestDTO) {
        Problem problem=problemDAO.findById(id);
        return problem.update(problem.getTitle(),problem.getContent(),problem.getExamples());
    }

    @Override
    public Page<Problem> showProblemAll(Pageable pageable) {

        return problemDAO.findAll(pageable);
    }
    @Override
    public Page<Problem> searchProblems(Pageable pageable,ProblemRequestDTO problemRequestDTO) {
        return problemDAO.search(pageable,problemRequestDTO.getTitle());
    }

    @Override
    public List<ProblemSearchResponseDTO> findIdAndTitle() {
        return problemDAO.findIdAndTitle();
    }
}
