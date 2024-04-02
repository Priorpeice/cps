package server.cps.submission.dao;

import lombok.RequiredArgsConstructor;
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
        return null;
    }

    @Override
    public Optional<Submission> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Submission submission) {

    }
}
