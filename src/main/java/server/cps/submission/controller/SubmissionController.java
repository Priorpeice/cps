package server.cps.submission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import server.cps.common.CpsResponse;
import server.cps.common.ResponseBody;
import server.cps.common.Status;
import server.cps.common.page.PageResponse;
import server.cps.common.page.Pageinfo;
import server.cps.compile.service.CompilerSelectService;
import server.cps.entity.Member;
import server.cps.entity.Problem;
import server.cps.entity.Submission;
import server.cps.member.service.MemberSevice;
import server.cps.submission.dto.SubmissionListResult;
import server.cps.submission.dto.SubmissionResult;
import server.cps.problem.service.ProblemService;
import server.cps.submission.dto.SubmissionInfoDTO;
import server.cps.submission.dto.SubmissionRequstDTO;
import server.cps.submission.mapper.SubmissionMapper;
import server.cps.submission.service.ScoreService;
import server.cps.submission.service.SubmissionService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SubmissionController {
    private final CompilerSelectService compilerSelectService;
    private final ScoreService ScoreService;
    private final MemberSevice memberSevice;
    private final SubmissionService submissionService;
    private final ProblemService problemService;
    private final SubmissionMapper submissionMapper;
    @PostMapping("/api/submit")
    public ResponseEntity<ResponseBody<SubmissionResult>> markCode(@RequestBody SubmissionRequstDTO submissionRequstDTO, @AuthenticationPrincipal UserDetails userDetails) {
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

    @GetMapping("/api/submissions")
    public ResponseEntity<ResponseBody<PageResponse<SubmissionListResult>>> getAllSubmission (@PageableDefault(page = 0, size = 10) Pageable pageable)
    {
        Page<Submission> submissions = submissionService.search(pageable);
        Pageinfo pageinfo= new Pageinfo(submissions,pageable);
        List<SubmissionListResult> dtoList = submissionMapper.toDtoList(submissions);
        PageResponse<SubmissionListResult> pageResponse = PageResponse.<SubmissionListResult>builder()
                .content(dtoList)
                .pageinfo(pageinfo)
                .build();
        return CpsResponse.toResponse(Status.READ,pageResponse);
    }
    @GetMapping("/api/submissions/search")
    public ResponseEntity<ResponseBody<PageResponse<SubmissionListResult>>> getSearchSubmission (@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam("title") String title)
    {
        Page<Submission> submissions = submissionService.search(pageable);
        Pageinfo pageinfo= new Pageinfo(submissions,pageable);
        List<SubmissionListResult> dtoList = submissionMapper.toDtoList(submissions);
        PageResponse<SubmissionListResult> pageResponse = PageResponse.<SubmissionListResult>builder()
                .content(dtoList)
                .pageinfo(pageinfo)
                .build();
        return CpsResponse.toResponse(Status.READ,pageResponse);
    }
}
