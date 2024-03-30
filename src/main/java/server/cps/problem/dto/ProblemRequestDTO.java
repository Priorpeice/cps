package server.cps.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.cps.entity.Example;
import server.cps.entity.Problem;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemRequestDTO {
    private String title;
    private String content;
    private List<String> inputs;
    private List<String> outputs;

    public Problem toEntity() {
        return Problem.builder()
                .title(this.title)
                .content(this.content)
                .build();

    }

    //login 위치 바꾸기?
    private Example toEntity(String input, String output,Problem problem){
        return Example.builder()
                .input(input)
                .output(output)
                .problem(problem)
                .build();
    }
    public List<Example> toEntitys(Problem problem){
        List<Example> examples = new ArrayList<>();
       for(int i = 0 ;i< inputs.size();i++){
           Example example=toEntity(inputs.get(i),outputs.get(i),problem);
           examples.add(example);
       }
       return examples;
    }

}
