package server.cps.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.cps.entity.Submission;
import server.cps.submission.dao.SubmissionDAO;
import server.cps.submission.dto.SubmissionInfoDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService{
    private final SubmissionDAO submissionDAO;

    @Override
    public Submission submissionSave(SubmissionInfoDTO submissionInfoDTO) {
        Submission submission = Submission.builder()
                .code(submissionInfoDTO.getCode())
                .member(submissionInfoDTO.getMember())
                .problem(submissionInfoDTO.getProblem())
                .isAnswer(submissionInfoDTO.getIsSuccess())
                .build()
        ;
       return submissionDAO.save(submission);
    }

    @Override
    public Page<Submission> search(Pageable pageable) {
        return submissionDAO.findSubmissions(pageable);
    }
}
