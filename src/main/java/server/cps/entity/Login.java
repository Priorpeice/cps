package server.cps.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Login {
    @Id
    private Long seq;
    @Column(name = "login_id")
    private String id;
    @Column(name = "login_pw")
    private String pw;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;

}
