package server.cps.model;
// 문제 클래스

public class Problem {

    private final Long id;
    private final String title;
    private final String description;

    public Problem(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
