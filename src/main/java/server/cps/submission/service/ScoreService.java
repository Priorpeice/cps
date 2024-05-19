package server.cps.submission.service;

import server.cps.model.SubmissionResult;
import server.cps.submission.dto.SubmissionRequstDTO;

public interface ScoreService {
    SubmissionResult mark(SubmissionRequstDTO submissionRequstDTO);
}
