package server.cps.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter

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

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Submission> submissions = new ArrayList<>();
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Login login;

}
