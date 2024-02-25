package server.cps.respository.problem;

import org.springframework.stereotype.Component;
import server.cps.model.Problem;

import java.util.ArrayList;
import java.util.List;
@Component
public class MemoryProblemRepositoryImpl implements ProblemRespository {
    private final List<Problem> problems;

    public MemoryProblemRepositoryImpl() {
        this.problems = new ArrayList<>();
    }
    @Override
    public void createProblem(Problem problem) {
        problems.add(problem);
    }

    @Override
    public List<Problem> getAllProblems() {
        return new ArrayList<>(problems);
    }
}
