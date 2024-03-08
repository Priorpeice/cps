package server.cps.respository.problem;



import server.cps.dto.problem.Problem;

import java.util.List;

public interface ProblemRespository {
    void createProblem(Problem problem);

    List<Problem> getAllProblems();
}
