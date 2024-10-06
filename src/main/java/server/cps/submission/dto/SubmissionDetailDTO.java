package server.cps.submission.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmissionDetailDTO
{
    private final String code;
    private final String language;
    private final String problemId;
    private final Boolean isSuccess;
}
