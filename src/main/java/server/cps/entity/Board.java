package server.cps.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)//접근 레벨 프로텍ㅊ
@EntityListeners(AuditingEntityListener.class)
// 등록 날짜 수정 날짜 추가
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    @Column(name = "board_title")
    private String title;
    @Column(name = "board_content",columnDefinition = "TEXT")
    private String content;
    @CreatedDate
    @Column(name = "board_reg")
    private LocalDateTime regDate;
    @LastModifiedDate
    @Column(name = "board_upt")
    private  LocalDateTime uptDate;
    @Column(name = "board_view")
    private  Long viewCount;
    //member id 관계 설정해야함
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.viewCount = 0L;
        this.member = member;
    }
    public Board update(String title, String content){
        this.title = title;
        this.content = content;
        return this;
    }

    @Builder
    public Board(String title, String content){
        this.title = title;
        this.content = content;
        this.viewCount = 0L;
    }





}
