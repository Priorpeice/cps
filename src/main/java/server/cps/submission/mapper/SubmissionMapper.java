package server.cps.submission.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import server.cps.entity.Submission;
import server.cps.submission.dto.SubmissionListResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubmissionMapper {
    public static SubmissionListResult toDto(Submission submission) {
        return SubmissionListResult.builder()
                .code(submission.getCode())
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
}
