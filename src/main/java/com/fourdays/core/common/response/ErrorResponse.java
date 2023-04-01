package com.fourdays.core.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse<T> {

    private final T data;

    @Builder
    public ErrorResponse(T data) {
        this.data = data;
    }
}
