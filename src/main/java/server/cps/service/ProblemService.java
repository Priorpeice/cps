package server.cps.service;

import server.cps.dto.problem.Problem;
import server.cps.respository.CodeRepository;
import server.cps.respository.problem.ProblemRespository;

import java.util.List;


public class ProblemService {
    private final CodeRepository codeRepository;
    private final ProblemRespository problemRespository;

    public ProblemService(CodeRepository codeRepository ,ProblemRespository problemRespository) {
        this.codeRepository = codeRepository;
        this.problemRespository = problemRespository;
    }
    // 수정 필요
    

    public void createProblem(Problem problem){
        problemRespository.createProblem(problem);
    }
    public List<Problem>getAllProblems(){
        return problemRespository.getAllProblems();
    }
}

