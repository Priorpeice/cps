package server.cps.model;
// 문제 클래스


public class Problem {
    private static Long nextId = 1L;  // 추가된 부분
    private final Long id;
    private final String title;
    private final String description;
    private final String code;
    private final String input;
    private final String output;
    private final String sampleInput;
    private final String sampleOutput;

    public Problem(String title, String description,String code,String input,String output,String sampleInput,String sampleOutput) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.code = code;
        this.input = input;
        this.output =output;
        this.sampleInput=sampleInput;
        this.sampleOutput=sampleOutput;

    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
