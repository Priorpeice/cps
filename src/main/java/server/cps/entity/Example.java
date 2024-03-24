package server.cps.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Example {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "example_id")
    private Long id;
    @Column(name = "example_input")
    private String input;
    @Column(name = "example_output")
    private String output;
    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    private Problem problem;
}
