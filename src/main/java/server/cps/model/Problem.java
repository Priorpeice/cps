package server.cps.model;
// 문제 클래스

public class Problem {

    private Long id;
    private String title;
    private String description;

    public Problem(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
