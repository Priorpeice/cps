package server.cps.submission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.cps.entity.Submission;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    Submission save(Submission submission);
    Optional<Submission> findById(Long id);
    void delete(Submission submission);

    @Query("SELECT s FROM Submission s JOIN FETCH s.member m LEFT JOIN FETCH m.role LEFT JOIN FETCH m.login JOIN FETCH s.problem")
    Page<Submission> findAllSubmissionsWithMemberAndMemberAssociationsAndProblem(Pageable pageable);
}
