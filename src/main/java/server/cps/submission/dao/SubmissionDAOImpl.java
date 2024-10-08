package server.cps.submission.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import server.cps.entity.Submission;
import server.cps.submission.repository.SubmissionRepository;
@Repository
@RequiredArgsConstructor
public class SubmissionDAOImpl implements  SubmissionDAO{
    private final SubmissionRepository submissionRepository;
    @Override
    public Submission save(Submission submission) {
        return submissionRepository.save(submission);
    }

    @Override
    public Submission findById(Long id) {
        return submissionRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
    }

    @Override
    public void delete(Submission submission) {

    }

    @Override
    public Page<Submission> findSubmissions(Pageable pageable) {
        return submissionRepository.findAllSubmissionsWithMemberAndMemberAssociationsAndProblem(pageable);
    }

    @Override
    public Page<Submission> findByProblemId(Pageable pageable, Long problemId) {
        return submissionRepository.findSubmissionsWithMemberAndMemberAssociationsAndProblem(problemId, pageable);
    }
}
