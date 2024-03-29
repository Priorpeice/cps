package server.cps.problem.service;

import org.springframework.stereotype.Service;
import server.cps.problem.dto.ProblemRequstDTO;
import server.cps.model.CompilationResult;
import server.cps.model.SubmissionResult;
import server.cps.compile.repository.CodeRepository;

import java.util.List;

@Service
public class SubmitService {
    private final CodeRepository codeRepository;
//    private final ProblemRespository problemRespository;


    public SubmitService(CodeRepository codeRepository ) {
        this.codeRepository = codeRepository;

    }
    // 수정 필요

    // 1. 테스트로 받아온 결과 채점 시스템 구현
    //

    public SubmissionResult mark(ProblemRequstDTO problemRequstDTO) {
        int totalScore = 0;

        List<String> expectedOutputs = codeRepository.readFilesFromFolder(problemRequstDTO.getProblemId());

        for (int i = 0; i < problemRequstDTO.getCompilationResults().size(); i++) {
            CompilationResult compileResult = problemRequstDTO.getCompilationResults().get(i);
            //시간 정하기
            double max= 3;
            double runningTime= extractRealTime(compileResult.getRunTime());
            String expectedOutput = expectedOutputs.get(i);
            if(max<=runningTime){
                return new SubmissionResult(problemRequstDTO.getCompilationResults(),totalScore,false);
            }
            if (compileResult.isCompile() && compileResult.getOutput().trim().equals(expectedOutput.trim())) {

                totalScore += 10; // 일단 문제당 10점 부여
            }
            else{
                return new SubmissionResult(problemRequstDTO.getCompilationResults(),totalScore,false);
            }
        }
        // SubmissionResult 객체 생성
        return new SubmissionResult(problemRequstDTO.getCompilationResults(), totalScore,true);
    }
    private static double extractRealTime(String runTimeString) {
        String[] tokens = runTimeString.split("\\s+");
        for (int i = 0; i < tokens.length - 1; i++) {
            if ("real".equals(tokens[i])) {
                try {
                    return Double.parseDouble(tokens[i + 1]);
                } catch (NumberFormatException e) {
                    // 예외 처리: 숫자로 변환할 수 없는 경우
                    e.printStackTrace();
                }
            }
        }
        // 기본값 또는 예외 처리를 원하는 방식으로 설정
        return 0.0;
    }

}

