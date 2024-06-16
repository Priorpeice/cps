package server.cps.submission.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.cps.entity.Submission;

import java.util.Optional;

public interface SubmissionDAO {
    Submission save(Submission submission);
    Optional<Submission> findById(Long id);
    void delete(Submission submission);
    Page<Submission> findSubmissions(Pageable pageable);
    Page<Submission> findByProblemId(Pageable pageable,Long problemId );
}
