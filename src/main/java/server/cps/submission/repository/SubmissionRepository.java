package server.cps.submission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.cps.entity.Submission;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    Submission save(Submission submission);
    Optional<Submission> findById(Long id);
    void delete(Submission submission);

}
