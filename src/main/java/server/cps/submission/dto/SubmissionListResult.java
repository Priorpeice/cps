package server.cps.submission.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class SubmissionListResult {
    private final String submissionId;
    private final String  language;
    private final Boolean isAnswer;
    private final String problemId;
    private final String nickname;

}
