package server.cps.submission.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.cps.entity.Submission;
import server.cps.submission.dto.SubmissionInfoDTO;

public interface SubmissionService {
    Submission submissionSave(SubmissionInfoDTO submissionInfoDTO);
    Page<Submission> search(Pageable pageable);
}
