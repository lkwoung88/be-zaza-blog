package zaza.techblog.global.common.request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class PageSearchRequest {

    @Positive(message = "page는 양수여야 합니다.")
    private int page;

    @Positive(message = "limit는 양수여야 합니다.")
    private int limit;

    @Builder
    public PageSearchRequest(int page, int limit) {

        this.page = page;
        this.limit = limit;
    }

    public int getPage() {

        if (page <= 0) {

            return 0;
        }

        return page - 1;
    }

    public int getLimit() {

        if (this.limit <= 0) {

            return 10;
        }

        return limit;
    }
}
