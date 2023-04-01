package com.fourdays.core.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BasicResponse<T> {

    private final T data;

    @Builder
    public BasicResponse(T data) {
        this.data = data;
    }
}
