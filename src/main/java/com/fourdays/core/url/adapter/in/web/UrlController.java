package com.fourdays.core.url.adapter.in.web;

import com.fourdays.core.common.response.BasicResponse;
import com.fourdays.core.url.adapter.in.web.in.ShortenUrlDto;
import com.fourdays.core.url.adapter.in.web.out.UrlKeyDto;
import com.fourdays.core.url.application.port.in.UrlService;
import com.fourdays.core.url.adapter.in.web.out.OriginalUrlDto;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    @ResponseStatus(CREATED)
    @PostMapping
    public BasicResponse<UrlKeyDto> shortenUrl(@Validated @RequestBody ShortenUrlDto shortenUrlDto) {
        String url = shortenUrlDto.url();
        log.info("url={}", url);
        String urlKey = urlService.shortenUrl(url);
        return BasicResponse.<UrlKeyDto>builder()
                .data(new UrlKeyDto(urlKey))
                .build();
    }

    @GetMapping("/{urlKey}")
    public ResponseEntity<BasicResponse<OriginalUrlDto>> findOriginalUrlByKey(
            @PathVariable
            @Pattern(message = "invalid urlKey", regexp = "^[a-zA-Z0-9]{8}$")
            String urlKey
    ) {
        String originalUrl = urlService.findOriginalUrlByUrlKey(urlKey);
        return ResponseEntity
                .status(originalUrl == null ? NO_CONTENT : OK)
                .body(BasicResponse.<OriginalUrlDto>builder()
                        .data(new OriginalUrlDto(originalUrl))
                        .build());
    }
}
