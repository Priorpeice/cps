package server.cps.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)//접근 레벨 프로텍
@EntityListeners(AuditingEntityListener.class)

public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(name = "comment_content",columnDefinition = "TEXT")
    private String content;
    @Column(name = "comment_reg")
    @CreatedDate
    private LocalDateTime regDate;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name= "member_id")
    private Member member;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name= "board_id")
    private Board board;

    @Builder
    public Comment(String content, Board board, Member member){
        this.content = content;
        this.board =board;
        this.member =member;
    }
    @Builder
    public Comment(String content , Board board){
        this.content = content;
        this.board = board;
    }

}
