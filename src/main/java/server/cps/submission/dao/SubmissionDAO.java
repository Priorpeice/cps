package server.cps.submission.dao;

import server.cps.entity.Submission;

import java.util.Optional;

public interface SubmissionDAO {
    Submission save(Submission submission);
    Optional<Submission> findById(Long id);
    void delete(Submission submission);

}
