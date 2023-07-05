package com.fourdays.core.url.adapter.in.web.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ShortenUrlDto(
        @NotNull(message = "invalid url")
        @Pattern(message = "invalid url", regexp = "^(http(s)?://)([a-z0-9\\w]+\\.*)+\\.[a-z0-9]{2,}(/[a-zA-Z0-9]*)?$")
        String url
) {
}
