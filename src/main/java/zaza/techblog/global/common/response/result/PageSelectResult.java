package zaza.techblog.global.common.response.result;

import lombok.Getter;

@Getter
public class PageSelectResult <T> extends SelectResult <T> {

    private int totalPage;

    public PageSelectResult(T data, int totalPage) {

        super(data);
        this.totalPage = totalPage;
    }

    public PageSelectResult(T data, int totalCount, int limit) {

        super(data);
        this.totalPage = (totalCount / limit) + (totalCount % limit == 0 ? 0 : 1);
    }
}
