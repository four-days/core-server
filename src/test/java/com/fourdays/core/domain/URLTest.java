package com.fourdays.core.domain;

import com.fourdays.core.common.util.Base62Encoder;
import com.fourdays.core.model.domain.URL;
import com.fourdays.core.model.domain.exception.*;
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
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .urlKey(urlKey)
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
                .protocol("https")
                .domain("four.days")
                .port(null)
                .path("/")
                .urlKey(urlKey)
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
                .protocol("https")
                .domain("four.days")
                .port(null)
                .path("/")
                .urlKey(urlKey)
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
                .protocol("https")
                .domain("four.days")
                .port(null)
                .path(null)
                .urlKey(urlKey)
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
        assertThatThrownBy(() -> new URL(null, "four.days", 80, "/", Base62Encoder.encode(1L)))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + null);
    }

    @Test
    @DisplayName("constructor > parameters > protocol is invalid")
    void constructorTest_parameters_protocolIsInvalid() {
        assertThatThrownBy(() -> new URL("ftp", "four.days", 80, "/", Base62Encoder.encode(1L)))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + "ftp");
    }

    @Test
    @DisplayName("constructor > parameters > domain is null")
    void constructorTest_parameters_domainIsNull() {
        assertThatThrownBy(() -> new URL("http", null, 80, "/", Base62Encoder.encode(1L)))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage("domain is invalid. domain=" + null);
    }

    @Test
    @DisplayName("constructor > parameters > port is negative")
    void constructorTest_parameters_portIsNegative() {
        assertThatThrownBy(() -> new URL("http", "four.days", -1, "/", Base62Encoder.encode(1L)))
                .isInstanceOf(InvalidPortException.class)
                .hasMessage("port is invalid. port=" + -1);
    }

    @Test
    @DisplayName("constructor > parameters > path is invalid")
    void constructorTest_parameters_pathIsInvalid() {
        assertThatThrownBy(() -> new URL("http", "four.days", 80, "invalidString", Base62Encoder.encode(1L)))
                .isInstanceOf(InvalidPathException.class)
                .hasMessage("path is invalid. path=" + "invalidString");
    }

    @Test
    @DisplayName("constructor > originalString > port exists")
    void constructorTest_originalString_portExists() {
        String urlKey = Base62Encoder.encode(System.nanoTime());
        URL url = new URL("http://four.days:8080/", urlKey);
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
        URL httpUrl = new URL("http://four.days/", urlKey1);
        assertThat(httpUrl.getProtocol()).isEqualTo("http");
        assertThat(httpUrl.getDomain()).isEqualTo("four.days");
        assertThat(httpUrl.getPort()).isEqualTo(80);
        assertThat(httpUrl.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(httpUrl.getOriginal()).isEqualTo("http://four.days/");
        assertThat(httpUrl.getUrlKey()).isEqualTo(urlKey1);

        String urlKey2 = Base62Encoder.encode(System.nanoTime());
        URL httpsUrl = new URL("https://four.days/", urlKey2);
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
        URL url = new URL("http://four.days/", urlKey);
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
        URL url = new URL("http://four.days", urlKey);
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
        assertThatThrownBy(() -> new URL("://four.days:-1/", Base62Encoder.encode(1L)))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + "");
    }

    @Test
    @DisplayName("constructor > originalString > protocol is invalid")
    void constructorTest_originalString_protocolIsInvalid() {
        assertThatThrownBy(() -> new URL("ftp://four.days:80/", Base62Encoder.encode(1L)))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + "ftp");
    }

    @Test
    @DisplayName("constructor > originalString > domain is null")
    void constructorTest_originalString_domainIsNull() {
        assertThatThrownBy(() -> new URL("http://", Base62Encoder.encode(1L)))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage("domain is empty");
    }

    @Test
    @DisplayName("constructor > originalString > port is negative")
    void constructorTest_originalString_portIsNegative() {
        assertThatThrownBy(() -> new URL("http://four.days:-1/", Base62Encoder.encode(1L)))
                .isInstanceOf(InvalidPortException.class)
                .hasMessage("port is invalid. port=" + -1);
    }

    @Test
    @DisplayName("constructor > parameter > urlKey is null")
    void constructorTest_parameter_urlKeyIsNull() {
        assertThatThrownBy(() -> URL.builder()
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .urlKey(null)
                .build())
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=null");
    }

    @Test
    @DisplayName("constructor > originalString > urlKey is null")
    void constructorTest_originalString_urlKeyIsNull() {
        assertThatThrownBy(() -> new URL("https://four.days:443/hello", null))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=null");
    }

    @Test
    @DisplayName("constructor > parameter > urlKey contains '/'")
    void constructorTest_parameter_urlKeyContainsSlash() {
        assertThatThrownBy(() -> URL.builder()
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .urlKey("ABCD/EFG")
                .build())
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD/EFG");
    }

    @Test
    @DisplayName("constructor > originalString > urlKey contains '/'")
    void constructorTest_originalString_urlKeyContainsSlash() {
        assertThatThrownBy(() -> new URL("https://four.days:443/hello", "ABCD/EFG"))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD/EFG");
    }

    @Test
    @DisplayName("constructor > parameter > urlKey contains '+'")
    void constructorTest_parameter_urlKeyContainsPlus() {
        assertThatThrownBy(() -> URL.builder()
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .urlKey("ABCD+EFG")
                .build())
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD+EFG");
    }

    @Test
    @DisplayName("constructor > originalString > urlKey contains '+'")
    void constructorTest_originalString_urlKeyContainsPlus() {
        assertThatThrownBy(() -> new URL("https://four.days:443/hello", "ABCD+EFG"))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD+EFG");
    }

    @Test
    @DisplayName("constructor > parameter > urlKey contains '='")
    void constructorTest_parameter_urlKeyContainsEqual() {
        assertThatThrownBy(() -> URL.builder()
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .urlKey("ABCD=EFG")
                .build())
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD=EFG");
    }

    @Test
    @DisplayName("constructor > originalString > urlKey contains '='")
    void constructorTest_originalString_urlKeyContainsEqual() {
        assertThatThrownBy(() -> new URL("https://four.days:443/hello", "ABCD=EFG"))
                .isInstanceOf(InvalidUrlKeyException.class)
                .hasMessage("urlKey is invalid. urlKey=ABCD=EFG");
    }

    @Test
    @DisplayName("changeSeq")
    void changeSeqTest() {
        URL url = URL.builder()
                .protocol("https")
                .domain("four.days")
                .port(443)
                .path("/")
                .urlKey("ABCDEFG")
                .build();

        assertThat(url.getSeq()).isNull();

        url.changeSeq(1L);
        assertThat(url.getSeq()).isEqualTo(1L);
    }
}