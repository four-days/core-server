package com.fourdays.core.domain.url.model.entity;

import com.fourdays.core.common.util.Base62Encoder;
import com.fourdays.core.domain.url.model.entity.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class URLTest {

    @Test
    @DisplayName("constructor > parameters > port exists")
    void constructorTest_parameters_portExists() {
        String urlKey = Base62Encoder.encode(System.nanoTime());
        URL url = URL.builder()
                .urlKey(urlKey)
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build();
        assertThat(url.getProtocol()).isEqualTo("https");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(443);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("https://four.days:443/");
        assertThat(url.getUrlKey()).isEqualTo(urlKey);
    }

    @Test
    @DisplayName("constructor > parameters > port not exists")
    void constructorTest_parameters_portNotExists() {
        String urlKey = Base62Encoder.encode(System.nanoTime());
        URL url = URL.builder()
                .urlKey(urlKey)
                .protocol("https")
                .domain("four.days")
                .port(null)
                .path("/")
                .build();
        assertThat(url.getProtocol()).isEqualTo("https");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(443);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("https://four.days/");
        assertThat(url.getUrlKey()).isEqualTo(urlKey);
    }

    @Test
    @DisplayName("constructor > parameters > path exists")
    void constructorTest_parameters_pathExists() {
        String urlKey = Base62Encoder.encode(System.nanoTime());
        URL url = URL.builder()
                .urlKey(urlKey)
                .protocol("https")
                .domain("four.days")
                .port(null)
                .path("/")
                .build();
        assertThat(url.getProtocol()).isEqualTo("https");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(443);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("https://four.days/");
        assertThat(url.getUrlKey()).isEqualTo(urlKey);
    }

    @Test
    @DisplayName("constructor > parameters > path not exists")
    void constructorTest_parameters_pathNotExists() {
        String urlKey = Base62Encoder.encode(System.nanoTime());
        URL url = URL.builder()
                .urlKey(urlKey)
                .protocol("https")
                .domain("four.days")
                .port(null)
                .path(null)
                .build();
        assertThat(url.getProtocol()).isEqualTo("https");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(443);
        assertThat(url.getPath()).isEmpty();
        assertThat(url.getOriginal()).isEqualTo("https://four.days");
        assertThat(url.getUrlKey()).isEqualTo(urlKey);
    }

    @Test
    @DisplayName("constructor > parameters > protocol is null")
    void constructorTest_parameters_protocolIsNull() {
        assertThatThrownBy(() -> new URL(Base62Encoder.encode(1L), null, "four.days", 80, "/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + null);
    }

    @Test
    @DisplayName("constructor > parameters > protocol is invalid")
    void constructorTest_parameters_protocolIsInvalid() {
        assertThatThrownBy(() -> new URL(Base62Encoder.encode(1L), "ftp", "four.days", 80, "/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + "ftp");
    }

    @Test
    @DisplayName("constructor > parameters > domain is null")
    void constructorTest_parameters_domainIsNull() {
        assertThatThrownBy(() -> new URL(Base62Encoder.encode(1L), "http", null, 80, "/"))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage("domain is invalid. domain=" + null);
    }

    @Test
    @DisplayName("constructor > parameters > port is negative")
    void constructorTest_parameters_portIsNegative() {
        assertThatThrownBy(() -> new URL(Base62Encoder.encode(1L), "http", "four.days", -1, "/"))
                .isInstanceOf(InvalidPortException.class)
                .hasMessage("port is invalid. port=" + -1);
    }

    @Test
    @DisplayName("constructor > parameters > path is invalid")
    void constructorTest_parameters_pathIsInvalid() {
        assertThatThrownBy(() -> new URL(Base62Encoder.encode(1L), "http", "four.days", 80, "invalidString"))
                .isInstanceOf(InvalidPathException.class)
                .hasMessage("path is invalid. path=" + "invalidString");
    }

    @Test
    @DisplayName("constructor > originalString > port exists")
    void constructorTest_originalString_portExists() {
        String urlKey = Base62Encoder.encode(System.nanoTime());
        URL url = new URL(urlKey, "http://four.days:8080/");
        assertThat(url.getProtocol()).isEqualTo("http");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(8080);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("http://four.days:8080/");
        assertThat(url.getUrlKey()).isEqualTo(urlKey);
    }

    @Test
    @DisplayName("constructor > originalString > port not exists")
    void constructorTest_originalString_portNotExists() {
        String urlKey1 = Base62Encoder.encode(System.nanoTime());
        URL httpUrl = new URL(urlKey1, "http://four.days/");
        assertThat(httpUrl.getProtocol()).isEqualTo("http");
        assertThat(httpUrl.getDomain()).isEqualTo("four.days");
        assertThat(httpUrl.getPort()).isEqualTo(80);
        assertThat(httpUrl.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(httpUrl.getOriginal()).isEqualTo("http://four.days/");
        assertThat(httpUrl.getUrlKey()).isEqualTo(urlKey1);

        String urlKey2 = Base62Encoder.encode(System.nanoTime());
        URL httpsUrl = new URL(urlKey2, "https://four.days/");
        assertThat(httpsUrl.getProtocol()).isEqualTo("https");
        assertThat(httpsUrl.getDomain()).isEqualTo("four.days");
        assertThat(httpsUrl.getPort()).isEqualTo(443);
        assertThat(httpsUrl.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(httpsUrl.getOriginal()).isEqualTo("https://four.days/");
        assertThat(httpsUrl.getUrlKey()).isEqualTo(urlKey2);
    }

    @Test
    @DisplayName("constructor > originalString > path exists")
    void constructorTest_originalString_pathExists() {
        String urlKey = Base62Encoder.encode(System.nanoTime());
        URL url = new URL(urlKey, "http://four.days/");
        assertThat(url.getProtocol()).isEqualTo("http");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(80);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("http://four.days/");
        assertThat(url.getUrlKey()).isEqualTo(urlKey);
    }

    @Test
    @DisplayName("constructor > originalString > path not exists")
    void constructorTest_originalString_pathNotExists() {
        String urlKey = Base62Encoder.encode(System.nanoTime());
        URL url = new URL(urlKey, "http://four.days");
        assertThat(url.getProtocol()).isEqualTo("http");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(80);
        assertThat(url.getPath()).isEmpty();
        assertThat(url.getOriginal()).isEqualTo("http://four.days");
        assertThat(url.getUrlKey()).isEqualTo(urlKey);
    }

    @Test
    @DisplayName("constructor > originalString > protocol is null")
    void constructorTest_originalString_protocolIsNull() {
        assertThatThrownBy(() -> new URL(Base62Encoder.encode(1L), "://four.days:-1/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + "");
    }

    @Test
    @DisplayName("constructor > originalString > protocol is invalid")
    void constructorTest_originalString_protocolIsInvalid() {
        assertThatThrownBy(() -> new URL(Base62Encoder.encode(1L), "ftp://four.days:80/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + "ftp");
    }

    @Test
    @DisplayName("constructor > originalString > domain is null")
    void constructorTest_originalString_domainIsNull() {
        assertThatThrownBy(() -> new URL(Base62Encoder.encode(1L), "http://"))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage("domain is empty");
    }

    @Test
    @DisplayName("constructor > originalString > port is negative")
    void constructorTest_originalString_portIsNegative() {
        assertThatThrownBy(() -> new URL(Base62Encoder.encode(1L), "http://four.days:-1/"))
                .isInstanceOf(InvalidPortException.class)
                .hasMessage("port is invalid. port=" + -1);
    }

    @Test
    @DisplayName("constructor > parameter > urlKey is null")
    void constructorTest_parameter_urlKeyIsNull() {
        assertThatThrownBy(() -> URL.builder()
                .urlKey(null)
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build())
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=null");
    }

    @Test
    @DisplayName("constructor > originalString > urlKey is null")
    void constructorTest_originalString_urlKeyIsNull() {
        assertThatThrownBy(() -> new URL(null, "https://four.days:443/hello"))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=null");
    }

    @Test
    @DisplayName("constructor > parameter > urlKey contains '/'")
    void constructorTest_parameter_urlKeyContainsSlash() {
        assertThatThrownBy(() -> URL.builder()
                .urlKey("ABCD/EFG")
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build())
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD/EFG");
    }

    @Test
    @DisplayName("constructor > originalString > urlKey contains '/'")
    void constructorTest_originalString_urlKeyContainsSlash() {
        assertThatThrownBy(() -> new URL("ABCD/EFG", "https://four.days:443/hello"))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD/EFG");
    }

    @Test
    @DisplayName("constructor > parameter > urlKey contains '+'")
    void constructorTest_parameter_urlKeyContainsPlus() {
        assertThatThrownBy(() -> URL.builder()
                .urlKey("ABCD+EFG")
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build())
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD+EFG");
    }

    @Test
    @DisplayName("constructor > originalString > urlKey contains '+'")
    void constructorTest_originalString_urlKeyContainsPlus() {
        assertThatThrownBy(() -> new URL("ABCD+EFG", "https://four.days:443/hello"))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD+EFG");
    }

    @Test
    @DisplayName("constructor > parameter > urlKey contains '='")
    void constructorTest_parameter_urlKeyContainsEqual() {
        assertThatThrownBy(() -> URL.builder()
                .urlKey("ABCD=EFG")
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .build())
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD=EFG");
    }

    @Test
    @DisplayName("constructor > originalString > urlKey contains '='")
    void constructorTest_originalString_urlKeyContainsEqual() {
        assertThatThrownBy(() -> new URL("ABCD=EFG", "https://four.days:443/hello"))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD=EFG");
    }
}