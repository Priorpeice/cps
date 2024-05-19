package server.cps.submission.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.cps.entity.Member;
import server.cps.entity.Problem;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class SubmissionInfoDTO {
    private final String code;
    private final String language;
    private final String problemId;
    private final Boolean isSuccess;
    private final Member member;
    private final Problem problem;
}
