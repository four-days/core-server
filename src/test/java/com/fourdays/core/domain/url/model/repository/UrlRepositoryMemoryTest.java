package com.fourdays.core.domain.url.model.repository;

import com.fourdays.core.domain.url.model.entity.URL;
import com.fourdays.core.domain.url.model.entity.exception.InvalidKeyException;
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
        URL url = URL.builder()
                .key("ABCDEFG")
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build();

        URL savedUrl = urlRepository.save(url);
        assertThat(savedUrl).isEqualTo(url);
    }

    @Test
    @DisplayName("key 는 null 이어서는 안 된다.")
    void saveTest_keyIsNull() throws IllegalAccessException, NoSuchFieldException {
        URL url = URL.builder()
                .key("null")
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build();

        Class<URL> urlClass = URL.class;
        Field keyField = urlClass.getDeclaredField("key");
        keyField.setAccessible(true);
        keyField.set(url, null);

        assertThatThrownBy(() -> urlRepository.save(url))
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=null");
    }

    @Test
    @DisplayName("key 는 빈 문자열이어서는 안 된다.")
    void saveTest_keyIsEmpty() throws IllegalAccessException, NoSuchFieldException {
        URL url = URL.builder()
                .key("temp")
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build();

        Class<URL> urlClass = URL.class;
        Field keyField = urlClass.getDeclaredField("key");
        keyField.setAccessible(true);
        keyField.set(url, "");

        assertThatThrownBy(() -> urlRepository.save(url))
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=");
    }

    @Test
    @DisplayName("key 로 URL 객체를 조회할 수 있다.")
    void findByKeyTest_exists() {
        URL url = URL.builder()
                .key("key")
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build();
        urlRepository.save(url);

        Optional<URL> optionalUrl = urlRepository.findByKey("key");
        assertThat(optionalUrl).isNotEmpty();

        URL findUrl = optionalUrl.orElseThrow();
        assertThat(findUrl).isEqualTo(url);
    }

    @Test
    @DisplayName("존재하지 않는 key 로 URL 객체를 조회하면 빈 Optional 객체가 반환된다.")
    void findByKeyTest_notExists() {
        Optional<URL> empty = urlRepository.findByKey("none");
        assertThat(empty).isEmpty();
    }
}