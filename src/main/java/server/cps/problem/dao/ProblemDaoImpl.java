package server.cps.problem.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.cps.entity.Problem;
import server.cps.problem.dto.ProblemSearchResponseDTO;
import server.cps.problem.repository.ProblemRespository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProblemDaoImpl implements ProblemDAO{
    private final ProblemRespository problemRespository;
    @Override
    public Problem save(Problem problem) {
        return problemRespository.save(problem);
    }

    @Override
    public Problem findById(Long id) {

        return problemRespository.findById(id).orElseThrow(()-> new EntityNotFoundException());
    }

    @Override
    public List<Problem> findAll() {
        return problemRespository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Problem problem=problemRespository.findById(id).orElseThrow(()-> new EntityNotFoundException());
        problemRespository.delete(problem);
    }

    @Override
    public List<Problem> search(String title) {
        return problemRespository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<ProblemSearchResponseDTO> findIdAndTitle() {
        return problemRespository.findIdAndTitle();
    }
}
