package server.cps.common.page;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class PageResponse<T> {
    List<T> content;
    Pageinfo pageinfo;
}
