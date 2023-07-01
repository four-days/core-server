package com.fourdays.core.domain.url.model.service;

import com.fourdays.core.domain.url.model.entity.URL;
import com.fourdays.core.domain.url.model.repository.UrlRepositoryMemory;
import com.fourdays.core.domain.url.util.Base62EncoderUsingNanoTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlServiceImplTest {

    @Test
    @DisplayName("url 을 전달하면 url 을 단축해서 key 값을 반환한다.")
    void shortenUrlTest_keyCheck() {
        UrlService urlService = new UrlServiceImpl(new UrlRepositoryMemory(), () -> "key");
        String originalUrl = "https://four.days/";
        String key = urlService.shortenUrl(originalUrl);

        assertThat(key).isEqualTo("key");
    }

    @Test
    @DisplayName("key 로 원본 url 을 찾을 수 있다.")
    void findOriginalUrlByKeyTest_exists() {
        UrlRepositoryMemory urlRepository = new UrlRepositoryMemory();
        urlRepository.save(new URL("key", "https://four.days/"));
        UrlServiceImpl urlService = new UrlServiceImpl(urlRepository, new Base62EncoderUsingNanoTime());
        String originalUrl = urlService.findOriginalUrlByKey("key");
        assertThat(originalUrl).isEqualTo("https://four.days/");
    }

    @Test
    @DisplayName("유효하지 않은 key 를 전달하면 null 이 반환된다.")
    void findOriginalUrlByKeyTest_notExists() {
        UrlRepositoryMemory urlRepository = new UrlRepositoryMemory();
        UrlServiceImpl urlService = new UrlServiceImpl(urlRepository, new Base62EncoderUsingNanoTime());
        String originalUrl = urlService.findOriginalUrlByKey("key");
        assertThat(originalUrl).isNull();
    }
}