package server.cps.common.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class PageResponse<T> {
    @JsonProperty("content")
    List<T> content;
    Pageinfo pageinfo;
}
