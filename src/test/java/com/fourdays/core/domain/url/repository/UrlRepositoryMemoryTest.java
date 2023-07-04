package com.fourdays.core.domain.url.repository;

import com.fourdays.core.domain.url.entity.Protocol;
import com.fourdays.core.domain.url.entity.URL;
import com.fourdays.core.domain.url.entity.exception.InvalidUrlKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UrlRepositoryMemoryTest {

    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        urlRepository = new UrlRepositoryMemory();
    }

    @Test
    @DisplayName("URL 객체를 저장한다.")
    void saveTest_success() {
        Protocol https = new Protocol(1, "HTTPS");
        URL url = URL.builder()
                .urlKey("ABCDEFG")
                .protocol(https)
                .domain("four.days")
                .port(443)
                .path("/")
                .build();

        URL savedUrl = urlRepository.save(url);
        assertThat(savedUrl).isEqualTo(url);
    }

    @Test
    @DisplayName("urlKey 는 null 이어서는 안 된다.")
    void saveTest_urlKeyIsNull() throws IllegalAccessException, NoSuchFieldException {
        Protocol https = new Protocol(1, "HTTPS");
        URL url = URL.builder()
                .urlKey("null")
                .protocol(https)
                .domain("four.days")
                .port(443)
                .path("/")
                .build();

        Class<URL> urlClass = URL.class;
        Field urlKeyField = urlClass.getDeclaredField("urlKey");
        urlKeyField.setAccessible(true);
        urlKeyField.set(url, null);

        assertThatThrownBy(() -> urlRepository.save(url))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=" + null);
    }

    @Test
    @DisplayName("urlKey 는 빈 문자열이어서는 안 된다.")
    void saveTest_urlKeyIsEmpty() throws IllegalAccessException, NoSuchFieldException {
        Protocol https = new Protocol(1, "HTTPS");
        URL url = URL.builder()
                .urlKey("temp")
                .protocol(https)
                .domain("four.days")
                .port(443)
                .path("/")
                .build();

        Class<URL> urlClass = URL.class;
        Field urlKeyField = urlClass.getDeclaredField("urlKey");
        urlKeyField.setAccessible(true);
        urlKeyField.set(url, "");

        assertThatThrownBy(() -> urlRepository.save(url))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=");
    }

    @Test
    @DisplayName("urlKey 로 URL 객체를 조회할 수 있다.")
    void findByKeyTest_exists() {
        Protocol https = new Protocol(1, "HTTPS");
        URL url = URL.builder()
                .urlKey("urlKey")
                .protocol(https)
                .domain("four.days")
                .port(443)
                .path("/")
                .build();
        urlRepository.save(url);

        Optional<URL> optionalUrl = urlRepository.findByUrlKey("urlKey");
        assertThat(optionalUrl).isNotEmpty();

        URL findUrl = optionalUrl.orElseThrow();
        assertThat(findUrl).isEqualTo(url);
    }

    @Test
    @DisplayName("존재하지 않는 urlKey 로 URL 객체를 조회하면 빈 Optional 객체가 반환된다.")
    void findByKeyTest_notExists() {
        Optional<URL> empty = urlRepository.findByUrlKey("none");
        assertThat(empty).isEmpty();
    }
}