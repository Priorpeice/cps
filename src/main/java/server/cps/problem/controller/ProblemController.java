package server.cps.problem.controller;


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
import server.cps.entity.Problem;
import server.cps.problem.dto.ProblemRequestDTO;
import server.cps.problem.dto.ProblemSearchResponseDTO;
import server.cps.problem.mapper.ProblemMapper;
import server.cps.problem.service.ProblemService;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProblemController {

    private  final ProblemService problemService;
    private final ProblemMapper problemMapper;
    @GetMapping("/api/problems")
    public ResponseEntity<ResponseBody<PageResponse<ProblemSearchResponseDTO>>> showProblems(@PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<Problem> problems = problemService.showProblemAll(pageable);
        Pageinfo pageinfo = new Pageinfo(problems,pageable);
        List<ProblemSearchResponseDTO> problemDtoList = problemMapper.toDtoList(problems);
        PageResponse<ProblemSearchResponseDTO> pageResponse = PageResponse.<ProblemSearchResponseDTO>builder()
                .content(problemDtoList)
                .pageinfo(pageinfo)
                .build();
        return CpsResponse.toResponse(Status.READ,pageResponse);
    }
    @GetMapping("/api/problems/search")
    public ResponseEntity<ResponseBody<PageResponse<ProblemSearchResponseDTO>>> searchProblems(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam("title") String title){
        ProblemRequestDTO problemRequestDTO = new ProblemRequestDTO();
        problemRequestDTO.setTitle(title);
        Page<Problem> problems = problemService.searchProblems(pageable,problemRequestDTO);
        Pageinfo pageinfo = new Pageinfo(problems,pageable);
        List<ProblemSearchResponseDTO> problemDtoList = problemMapper.toDtoList(problems);
        PageResponse<ProblemSearchResponseDTO> pageResponse = PageResponse.<ProblemSearchResponseDTO>builder()
                .content(problemDtoList)
                .pageinfo(pageinfo)
                .build();
        return CpsResponse.toResponse(Status.READ,pageResponse);
    }
    @GetMapping("/api/problem/{problemId}")
    public Problem getBoard(@PathVariable Long problemId)
    {
        return problemService.findById(problemId);
    }
    @PostMapping("/api/problem")
    public Problem createProblem(@RequestBody ProblemRequestDTO problemRequestDTO,@AuthenticationPrincipal UserDetails userDetails)
    {
        return problemService.saveProblem(problemRequestDTO);
    }



}
