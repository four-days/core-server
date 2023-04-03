package com.fourdays.core.domain.url.model.service;

import com.fourdays.core.domain.url.model.entity.URL;
import com.fourdays.core.domain.url.model.repository.UrlRepositoryMemory;
import com.fourdays.core.domain.url.util.Base62EncoderUsingNanoTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlServiceImplTest {

    @Test
    @DisplayName("shortenUrl > urlKeyCheck")
    void shortenUrlTest_urlKeyCheck() {
        UrlService urlService = new UrlServiceImpl(new UrlRepositoryMemory(), () -> "urlKey");
        String originalUrl = "https://four.days/";
        String urlKey = urlService.shortenUrl(originalUrl);

        assertThat(urlKey).isEqualTo("urlKey");
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > exists")
    void findOriginalUrlByUrlKeyTest_exists() {
        UrlRepositoryMemory urlRepository = new UrlRepositoryMemory();
        urlRepository.save(new URL("urlKey", "https://four.days/"));
        UrlServiceImpl urlService = new UrlServiceImpl(urlRepository, new Base62EncoderUsingNanoTime());
        String originalUrl = urlService.findOriginalUrlByUrlKey("urlKey");
        assertThat(originalUrl).isEqualTo("https://four.days/");
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > not exists")
    void findOriginalUrlByUrlKeyTest_notExists() {
        UrlRepositoryMemory urlRepository = new UrlRepositoryMemory();
        UrlServiceImpl urlService = new UrlServiceImpl(urlRepository, new Base62EncoderUsingNanoTime());
        String originalUrl = urlService.findOriginalUrlByUrlKey("urlKey");
        assertThat(originalUrl).isNull();
    }
}