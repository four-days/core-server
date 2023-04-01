package com.fourdays.core.domain.url.model.service;

import com.fourdays.core.domain.url.model.repository.UrlRepository;
import com.fourdays.core.domain.url.model.repository.UrlRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlServiceImplTest {

    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        urlRepository = new UrlRepositoryMemory();
    }

    @Test
    @DisplayName("shortenUrl > urlKeyCheck")
    void shortenUrlTest_urlKeyCheck() {
        UrlService urlService = new UrlServiceImpl(urlRepository, () -> "urlKey");
        String originalUrl = "https://four.days/";
        String urlKey = urlService.shortenUrl(originalUrl);

        assertThat(urlKey).isEqualTo("urlKey");
    }
}