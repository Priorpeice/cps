package server.cps.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter

public class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;
    @Column(name = "problem_title")
    private String title;
    @Column(name = "problem_content")
    private String content;
    @OneToMany(mappedBy = "problem",cascade = CascadeType.REMOVE)
    private List<Example> examples = new ArrayList<>();
    @OneToMany(mappedBy = "problem",cascade = CascadeType.REMOVE)
    private List<Submission> submissions = new ArrayList<>();

}
