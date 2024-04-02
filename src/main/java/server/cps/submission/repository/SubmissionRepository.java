package server.cps.submission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.cps.entity.Submission;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    Submission save(Submission submission);
    Optional<Submission> findById(Long id);
    void delete(Submission submission);
    @Query ("SELECT s FROM Submission s JOIN s.member m JOIN m.user u WHERE u.id = :userId")
    List<Submission> findByUserId(String userId);
}
