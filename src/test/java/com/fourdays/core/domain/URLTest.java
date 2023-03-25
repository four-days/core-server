package com.fourdays.core.domain;

import com.fourdays.core.domain.exception.InvalidDomainException;
import com.fourdays.core.domain.exception.InvalidPathException;
import com.fourdays.core.domain.exception.InvalidPortException;
import com.fourdays.core.domain.exception.InvalidProtocolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class URLTest {

    @Test
    @DisplayName("constructor > parameters > port exists")
    void constructorTest_parameters_portExists() {
        URL url = URL.builder()
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
    }

    @Test
    @DisplayName("constructor > parameters > port not exists")
    void constructorTest_parameters_portNotExists() {
        URL url = URL.builder()
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
    }

    @Test
    @DisplayName("constructor > parameters > path exists")
    void constructorTest_parameters_pathExists() {
        URL url = URL.builder()
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
    }

    @Test
    @DisplayName("constructor > parameters > path not exists")
    void constructorTest_parameters_pathNotExists() {
        URL url = URL.builder()
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
    }

    @Test
    @DisplayName("constructor > parameters > protocol is null")
    void constructorTest_parameters_protocolIsNull() {
        assertThatThrownBy(() -> new URL(null, "four.days", 80, "/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + null);
    }

    @Test
    @DisplayName("constructor > parameters > protocol is invalid")
    void constructorTest_parameters_protocolIsInvalid() {
        assertThatThrownBy(() -> new URL("ftp", "four.days", 80, "/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + "ftp");
    }

    @Test
    @DisplayName("constructor > parameters > domain is null")
    void constructorTest_parameters_domainIsNull() {
        assertThatThrownBy(() -> new URL("http", null, 80, "/"))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage("domain is invalid. domain=" + null);
    }

    @Test
    @DisplayName("constructor > parameters > port is negative")
    void constructorTest_parameters_portIsNegative() {
        assertThatThrownBy(() -> new URL("http", "four.days", -1, "/"))
                .isInstanceOf(InvalidPortException.class)
                .hasMessage("port is invalid. port=" + -1);
    }

    @Test
    @DisplayName("constructor > parameters > path is invalid")
    void constructorTest_parameters_pathIsInvalid() {
        assertThatThrownBy(() -> new URL("http", "four.days", 80, "invalidString"))
                .isInstanceOf(InvalidPathException.class)
                .hasMessage("path is invalid. path=" + "invalidString");
    }

    @Test
    @DisplayName("constructor > originalString > port exists")
    void constructorTest_originalString_portExists() {
        URL url = new URL("http://four.days:8080/");
        assertThat(url.getProtocol()).isEqualTo("http");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(8080);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("http://four.days:8080/");
    }

    @Test
    @DisplayName("constructor > originalString > port not exists")
    void constructorTest_originalString_portNotExists() {
        URL httpUrl = new URL("http://four.days/");
        assertThat(httpUrl.getProtocol()).isEqualTo("http");
        assertThat(httpUrl.getDomain()).isEqualTo("four.days");
        assertThat(httpUrl.getPort()).isEqualTo(80);
        assertThat(httpUrl.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(httpUrl.getOriginal()).isEqualTo("http://four.days/");

        URL httpsUrl = new URL("https://four.days/");
        assertThat(httpsUrl.getProtocol()).isEqualTo("https");
        assertThat(httpsUrl.getDomain()).isEqualTo("four.days");
        assertThat(httpsUrl.getPort()).isEqualTo(443);
        assertThat(httpsUrl.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(httpsUrl.getOriginal()).isEqualTo("https://four.days/");
    }

    @Test
    @DisplayName("constructor > originalString > path exists")
    void constructorTest_originalString_pathExists() {
        URL url = new URL("http://four.days/");
        assertThat(url.getProtocol()).isEqualTo("http");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(80);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("http://four.days/");
    }

    @Test
    @DisplayName("constructor > originalString > path not exists")
    void constructorTest_originalString_pathNotExists() {
        URL url = new URL("http://four.days");
        assertThat(url.getProtocol()).isEqualTo("http");
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(80);
        assertThat(url.getPath()).isEmpty();
        assertThat(url.getOriginal()).isEqualTo("http://four.days");
    }

    @Test
    @DisplayName("constructor > originalString > protocol is null")
    void constructorTest_originalString_protocolIsNull() {
        assertThatThrownBy(() -> new URL("://four.days:-1/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + "");
    }

    @Test
    @DisplayName("constructor > originalString > protocol is invalid")
    void constructorTest_originalString_protocolIsInvalid() {
        assertThatThrownBy(() -> new URL("ftp://four.days:80/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + "ftp");
    }

    @Test
    @DisplayName("constructor > originalString > domain is null")
    void constructorTest_originalString_domainIsNull() {
        assertThatThrownBy(() -> new URL("http://"))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage("domain is empty");
    }

    @Test
    @DisplayName("constructor > originalString > port is negative")
    void constructorTest_originalString_portIsNegative() {
        assertThatThrownBy(() -> new URL("http://four.days:-1/"))
                .isInstanceOf(InvalidPortException.class)
                .hasMessage("port is invalid. port=" + -1);
    }
}