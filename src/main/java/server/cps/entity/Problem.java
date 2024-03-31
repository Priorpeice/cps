package server.cps.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;
    @Column(name = "problem_title")
    private String title;
    @Column(name = "problem_content")
    private String content;
    /*persist도 같이 되야함*/
    @OneToMany(mappedBy = "problem",cascade = CascadeType.ALL)
    private List<Example> examples = new ArrayList<>();
    @OneToMany(mappedBy = "problem",cascade = CascadeType.REMOVE)
    private List<Submission> submissions = new ArrayList<>();
    @Builder
    public Problem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Problem update(String title, String content,List<Example> examples)
    {
        this.title = title;
        this.content = content;
        this.examples= examples;
        return this;
    }
    //예제 정보 넣기
    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }
}
