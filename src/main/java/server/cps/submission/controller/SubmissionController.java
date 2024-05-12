package server.cps.submission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.cps.common.CpsResponse;
import server.cps.common.ResoponseBody;
import server.cps.common.Status;
import server.cps.compile.service.CompilerSelectService;
import server.cps.entity.Member;
import server.cps.entity.Problem;
import server.cps.member.service.MemberSevice;
import server.cps.model.SubmissionResult;
import server.cps.problem.service.ProblemService;
import server.cps.submission.dto.SubmissionInfoDTO;
import server.cps.submission.dto.SubmissionRequstDTO;
import server.cps.submission.service.ScoreService;
import server.cps.submission.service.SubmissionService;

import java.io.IOException;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SubmissionController {
    private final CompilerSelectService compilerSelectService;
    private final ScoreService ScoreService;
    private final MemberSevice memberSevice;
    private final SubmissionService submissionService;
    private final ProblemService problemService;
    @PostMapping("/api/submit")
    public ResponseEntity<ResoponseBody<SubmissionResult>> markCode(@RequestBody SubmissionRequstDTO submissionRequstDTO,@AuthenticationPrincipal UserDetails userDetails) {
        String memberId = userDetails.getUsername();
        Member member = memberSevice.findMemberWithLoginid(memberId);
        Problem problem = problemService.findById(Long.parseLong(submissionRequstDTO.getProblemId()));
        submissionRequstDTO.setUserName(member.getName());
        try {
            submissionRequstDTO.setCompilationResults(compilerSelectService.getCompilerForLanguage(submissionRequstDTO.getLanguage()).testAndRun(submissionRequstDTO));
            SubmissionResult result = ScoreService.mark(submissionRequstDTO); //score sucess
            SubmissionInfoDTO submissionInfoDTO= SubmissionInfoDTO.builder()
                    .code(submissionRequstDTO.getCode())
                    .language(submissionRequstDTO.getLanguage())
                    .isSuccess(result.getSuccess())
                    .member(member)
                    .problem(problem)
                    .problemId(submissionRequstDTO.getProblemId())
                    .build();
            submissionService.submissionSave(submissionInfoDTO);
            return CpsResponse.toResponse(Status.SUCCESS,result);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
