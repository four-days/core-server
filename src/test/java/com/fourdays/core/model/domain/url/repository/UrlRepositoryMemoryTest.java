package com.fourdays.core.model.domain.url.repository;

import com.fourdays.core.model.domain.url.entity.URL;
import com.fourdays.core.model.domain.url.entity.exception.InvalidUrlKeyException;
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
    @DisplayName("save > success")
    void saveTest_success() {
        URL url = URL.builder()
                .urlKey("ABCDEFG")
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build();

        URL savedUrl = urlRepository.save(url);
        assertThat(savedUrl).isEqualTo(url);
    }

    @Test
    @DisplayName("save > urlKey is null")
    void saveTest_urlKeyIsNull() throws IllegalAccessException, NoSuchFieldException {
        URL url = URL.builder()
                .urlKey("temp")
                .protocol("https")
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
                .hasMessage("urlKey is invalid. urlKey=null");
    }

    @Test
    @DisplayName("save > urlKey is \"\"")
    void saveTest_urlKeyIsEmpty() throws IllegalAccessException, NoSuchFieldException {
        URL url = URL.builder()
                .urlKey("temp")
                .protocol("https")
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
    @DisplayName("findByUrlKey > exists")
    void findByUrlKeyTest_exists() throws Exception {
        URL url = URL.builder()
                .urlKey("key")
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build();
        urlRepository.save(url);

        Optional<URL> optionalUrl = urlRepository.findByUrlKey("key");
        assertThat(optionalUrl).isNotEmpty();

        URL findUrl = optionalUrl.orElseThrow();
        assertThat(findUrl).isEqualTo(url);
    }

    @Test
    @DisplayName("findByUrlKey > not exists")
    void findByUrlKeyTest_notExists() {
        Optional<URL> empty = urlRepository.findByUrlKey("none");
        assertThat(empty).isEmpty();
    }
}