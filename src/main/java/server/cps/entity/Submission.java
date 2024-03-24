package server.cps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class Submission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "submission_id")
    private Long id;
    @Column(name = "submission_code",columnDefinition = "Text")
    private String code;
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

}
