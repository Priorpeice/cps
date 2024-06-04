package server.cps.submission.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import server.cps.entity.Submission;
import server.cps.submission.repository.SubmissionRepository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class SubmissionDAOImpl implements  SubmissionDAO{
    private final SubmissionRepository submissionRepository;
    @Override
    public Submission save(Submission submission) {
        return submissionRepository.save(submission);
    }

    @Override
    public Optional<Submission> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Submission submission) {

    }

    @Override
    public Page<Submission> findSubmissions(Pageable pageable) {
        return submissionRepository.findAllSubmissionsWithMemberAndMemberAssociationsAndProblem(pageable);
    }
}
