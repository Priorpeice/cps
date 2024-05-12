package server.cps.common.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter

public class Pageinfo {
    private int pageNum; // 현재 페이지 번호
    private int listLimit; // 페이지 당 게시물 목록 갯수
    private long listCount; // 총 게시물 수
    private int maxPage; // 전체 페이지 수
    private boolean isFirst; // 첫 페이지 여부
    private boolean isLast; // 마지막 페이지 여부

    public Pageinfo(Pageable pageable) {
        this.pageNum = pageable.getPageNumber();
        this.listLimit = pageable.getPageSize();
        this.isFirst = pageable.getPageNumber() == 0;
    }
}
