package server.cps.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "member_id")
    private Long id;
    @Column(name= "member_name")
    private  String name;
    @Column(name= "member_nickname")
    private String nickname;
    @Column(name= "member_cellphone")
    private String phone;
    @Column(name= "member_student_id")
    private Long studentId;
    @Column(name= "member_email")
    private String email;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY ,cascade = CascadeType.REMOVE)
    private List<Board> boards = new ArrayList<>();
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Submission> submissions = new ArrayList<>();
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Login login;
    @Builder
    public Member(String name, String nickname, String phone, Long studentId, String email) {
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.studentId = studentId;
        this.email = email;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
