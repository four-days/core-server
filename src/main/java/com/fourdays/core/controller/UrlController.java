package com.fourdays.core.controller;

import com.fourdays.core.common.response.BasicResponse;
import com.fourdays.core.controller.dto.request.ShortenUrlDto;
import com.fourdays.core.controller.dto.response.KeyDto;
import com.fourdays.core.domain.url.service.UrlService;
import com.fourdays.core.controller.dto.response.OriginalUrlDto;
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
    public BasicResponse<KeyDto> shortenUrl(@Validated @RequestBody ShortenUrlDto shortenUrlDto) {
        String url = shortenUrlDto.url();
        log.info("url={}", url);
        String key = urlService.shortenUrl(url);
        return BasicResponse.<KeyDto>builder()
                .data(new KeyDto(key))
                .build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<BasicResponse<OriginalUrlDto>> findOriginalUrlByKey(
            @PathVariable
            @Pattern(message = "invalid key", regexp = "^[a-zA-Z0-9]{8}$")
            String key
    ) {
        String originalUrl = urlService.findOriginalUrlByKey(key);
        return ResponseEntity
                .status(originalUrl == null ? NO_CONTENT : OK)
                .body(BasicResponse.<OriginalUrlDto>builder()
                        .data(new OriginalUrlDto(originalUrl))
                        .build());
    }
}
