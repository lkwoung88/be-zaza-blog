package zaza.techblog.global.common.response.result;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.NONE)
public class SelectResult <T> {

    T data;

    @Builder
    public SelectResult(T data) {

        this.data = data;
    }
}
