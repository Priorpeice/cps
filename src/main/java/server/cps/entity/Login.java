package server.cps.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    @Id
    private Long seq;
    @Column(name = "login_id")
    private String id;
    @Column(name = "login_pw")
    private String pw;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;
    @Builder
    public Login(String id, String pw,Member member) {
        this.id = id;
        this.pw = pw;
        this.member= member;
    }
}
