package server.cps.submission.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class SubmissionListResult {
    private final String code;
    private final Boolean isAnswer;
}
