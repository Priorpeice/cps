package server.cps.submission.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import server.cps.entity.Submission;
import server.cps.submission.dto.SubmissionDetailDTO;
import server.cps.submission.dto.SubmissionListResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubmissionMapper {
    public static SubmissionListResult toDto(Submission submission) {
        return SubmissionListResult.builder()
                .submissionId(submission.getId().toString())
                .language(submission.getLanguage())
                .isAnswer(submission.getIsAnswer())
                .problemId(submission.getProblem().getId().toString())
                .nickname(submission.getMember().getNickname())
                .build();
    }
    public List<SubmissionListResult> toDtoList(Page<Submission> submissions) {
        return  submissions.getContent().stream()
                .map(SubmissionMapper::toDto)
                .collect(Collectors.toList());
    }

    public  SubmissionDetailDTO toDetailDto(Submission submission)
    {
        return SubmissionDetailDTO.builder()
                .code(submission.getCode())
                .language(submission.getLanguage())
                .isSuccess(submission.getIsAnswer())
                .problemId(submission.getProblem().getId().toString())
                .build();
    }
}
