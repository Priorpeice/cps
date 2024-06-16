package server.cps.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Submission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "submission_id")
    private Long id;
    @Column(name = "submission_code",columnDefinition = "Text")
    private String code;
    @Column(name ="submission_language")
    private String language;
    @Column(name="submission_is_answer")
    private Boolean isAnswer;
    @Column(name = "submission_reg")
    @CreatedDate
    private LocalDateTime regDate;
    @Column(name = "submission_upt")
    @LastModifiedDate
    private LocalDateTime uptDate;
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Problem problem;
    @Builder
    public Submission(String code, Boolean isAnswer, String language,Member member, Problem problem) {
        this.code = code;
        this.isAnswer = isAnswer;
        this.language= language;
        this.member = member;
        this.problem = problem;
    }
}
