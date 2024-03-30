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
public class Example {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "example_id")
    private Long id;
    @Column(name = "example_input")
    private String input;
    @Column(name = "example_output")
    private String output;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    private Problem problem;
    @Builder
    public Example(String input, String output,Problem problem) {
        this.input = input;
        this.output = output;
        this.problem = problem;
    }
}
