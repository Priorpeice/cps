package server.cps.service;

import org.springframework.stereotype.Service;
import server.cps.model.CompilationResult;
import server.cps.model.Problem;
import server.cps.model.SubmissionResult;
import server.cps.respository.CodeRepository;
import server.cps.respository.problem.ProblemRespository;

import java.io.IOException;
import java.util.List;

@Service
public class ProblemService {
    private final CodeRepository codeRepository;
    private final ProblemRespository problemRespository;

    public ProblemService(CodeRepository codeRepository ,ProblemRespository problemRespository) {
        this.codeRepository = codeRepository;
        this.problemRespository = problemRespository;
    }
    // 수정 필요
    public SubmissionResult submit(List<CompilationResult> compilationResults,String problemNumber) {
        int totalScore = 0;

        try {
            List<String> expectedOutputs = codeRepository.readFilesFromFolder(problemNumber, ".out");

                    for (int i = 0; i < compilationResults.size(); i++) {
                        CompilationResult compileResult = compilationResults.get(i);
                        String expectedOutput = expectedOutputs.get(i);

                        if (compileResult.isCompile() && compileResult.getOutput().trim().equals(expectedOutput.trim())) {
                            totalScore += 10; // 일단 문제당 10점 부여
                        }
                        else{
                            return new SubmissionResult(compilationResults,totalScore,false);
                        }
                    }


        } catch (IOException e) {
            String errorMessage = "평가 중 예외 발생: " + e.getMessage();
            compilationResults.add(new CompilationResult(errorMessage, false));
        }

        // SubmissionResult 객체 생성
        return new SubmissionResult(compilationResults, totalScore,true);
    }

    public void createProblem(Problem problem){
        problemRespository.createProblem(problem);
    }
    public List<Problem>getAllProblems(){
        return problemRespository.getAllProblems();
    }
}

