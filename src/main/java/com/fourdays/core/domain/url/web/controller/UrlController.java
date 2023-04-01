package com.fourdays.core.domain.url.web.controller;

import com.fourdays.core.common.response.BasicResponse;
import com.fourdays.core.domain.url.model.service.UrlService;
import com.fourdays.core.domain.url.web.controller.dto.request.ShortenUrlDto;
import com.fourdays.core.domain.url.web.controller.dto.response.UrlKeyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    @ResponseStatus(CREATED)
    @PostMapping
    public BasicResponse<UrlKeyDto> shortenUrl(@Validated @RequestBody ShortenUrlDto shortenUrlDto) {
        String urlKey = urlService.shortenUrl(shortenUrlDto.getUrl());
        return BasicResponse.<UrlKeyDto>builder()
                .data(new UrlKeyDto(urlKey))
                .build();
    }
}
