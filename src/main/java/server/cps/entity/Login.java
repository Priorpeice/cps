package server.cps.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Login {
    @Id
    private Long seq;

    @Column(name = "login_id",unique = true)
    private String id;
    @Column(name = "login_pw")
    private String pw;
    //사용자 한명당 하나의 권한
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    @MapsId
    private Member member;
    @Builder
    public Login(String id, String pw, Member member) {
        this.id = id;
        this.pw = pw;
        this.member= member;
    }
}
