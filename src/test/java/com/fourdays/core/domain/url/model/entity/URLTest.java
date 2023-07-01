package com.fourdays.core.domain.url.model.entity;

import com.fourdays.core.domain.url.model.entity.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class URLTest {


    @Test
    @DisplayName("port 를 지정해서 URL 객체를 생성할 수 있다.")
    void constructorTest_parameters_portExists() {
        Protocol https = new Protocol(1, "HTTPS");
        URL url = URL.builder()
                .key("ABCDEFG")
                .protocol(https)
                .domain("four.days")
                .port(443)
                .path("/")
                .build();

        assertThat(url.getKey()).isEqualTo("ABCDEFG");
        assertThat(url.getProtocol()).isEqualTo(https);
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(443);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("https://four.days:443/");
    }

    @Test
    @DisplayName("port 가 없이 URL 객체를 생성할 수 있다.")
    void constructorTest_parameters_portNotExists() {
        Protocol https = new Protocol(1, "HTTPS");
        URL url = URL.builder()
                .key("ABCDEFG")
                .protocol(https)
                .domain("four.days")
                .port(null)
                .path("/")
                .build();

        assertThat(url.getKey()).isEqualTo("ABCDEFG");
        assertThat(url.getProtocol()).isEqualTo(https);
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(443);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("https://four.days:443/");
    }

    @Test
    @DisplayName("path 를 지정해서 URL 객체를 생성할 수 있다.")
    void constructorTest_parameters_pathExists() {
        Protocol https = new Protocol(1, "HTTPS");
        URL url = URL.builder()
                .key("ABCDEFG")
                .protocol(https)
                .domain("four.days")
                .port(null)
                .path("/")
                .build();

        assertThat(url.getKey()).isEqualTo("ABCDEFG");
        assertThat(url.getProtocol()).isEqualTo(https);
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(443);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("https://four.days:443/");
    }

    @Test
    @DisplayName("path 가 없이 URL 객체를 생성할 수 있다.")
    void constructorTest_parameters_pathNotExists() {
        Protocol https = new Protocol(1, "HTTPS");
        URL url = URL.builder()
                .key("ABCDEFG")
                .protocol(https)
                .domain("four.days")
                .port(null)
                .path(null)
                .build();

        assertThat(url.getKey()).isEqualTo("ABCDEFG");
        assertThat(url.getProtocol()).isEqualTo(https);
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(443);
        assertThat(url.getPath()).isEmpty();
        assertThat(url.getOriginal()).isEqualTo("https://four.days:443");
    }

    @Test
    @DisplayName("protocol 없이 URL 객체를 생성할 수 없다.")
    void constructorTest_parameters_protocolIsNull() {
        assertThatThrownBy(() -> new URL("ABCDEFG", null, "four.days", 80, "/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is null.");
    }

    @Test
    @DisplayName("http 또는 https protocol 로만 URL 객체를 생성할 수 있다.")
    void constructorTest_parameters_protocolIsInvalid() {
        Protocol ftp = new Protocol(1, "ftp");
        assertThatThrownBy(() -> new URL("ABCDEFG", ftp, "four.days", 80, "/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + ftp);
    }

    @Test
    @DisplayName("domain 주소 없이 URL 객체를 생성할 수 없다.")
    void constructorTest_parameters_domainIsNull() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> new URL("ABCDEFG", http, null, 80, "/"))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage("domain is invalid. domain=" + null);
    }

    @Test
    @DisplayName("port 를 입력할 경우 양수만 입력할 수 있다.")
    void constructorTest_parameters_portIsNegative() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> new URL("ABCDEFG", http, "four.days", -1, "/"))
                .isInstanceOf(InvalidPortException.class)
                .hasMessage("port is invalid. port=" + -1);
    }

    @Test
    @DisplayName("path 는 존재하지 않거나 '/' 문자로 시작해야 한다.")
    void constructorTest_parameters_pathIsInvalid() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> new URL("ABCDEFG", http, "four.days", 80, "invalidString"))
                .isInstanceOf(InvalidPathException.class)
                .hasMessage("path is invalid. path=" + "invalidString");
    }

    @Test
    @DisplayName("port 를 지정해서 URL 객체를 생성할 수 있다.")
    void constructorTest_originalString_portExists() {
        Protocol http = new Protocol(1, "HTTP");
        URL url = new URL("ABCDEFG", http, "http://four.days:8080/");

        assertThat(url.getKey()).isEqualTo("ABCDEFG");
        assertThat(url.getProtocol()).isEqualTo(http);
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(8080);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("http://four.days:8080/");
    }

    @Test
    @DisplayName("port 가 없이 URL 객체를 생성할 수 있다.")
    void constructorTest_originalString_portNotExists() {
        Protocol http = new Protocol(1, "HTTP");
        URL httpUrl = new URL("ABCDEFG1", http, "http://four.days/");
        assertThat(httpUrl.getKey()).isEqualTo("ABCDEFG1");
        assertThat(httpUrl.getProtocol()).isEqualTo(http);
        assertThat(httpUrl.getDomain()).isEqualTo("four.days");
        assertThat(httpUrl.getPort()).isEqualTo(80);
        assertThat(httpUrl.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(httpUrl.getOriginal()).isEqualTo("http://four.days/");

        Protocol https = new Protocol(1, "HTTPS");
        URL httpsUrl = new URL("ABCDEFG2", https, "https://four.days/");
        assertThat(httpsUrl.getKey()).isEqualTo("ABCDEFG2");
        assertThat(httpsUrl.getProtocol()).isEqualTo(https);
        assertThat(httpsUrl.getDomain()).isEqualTo("four.days");
        assertThat(httpsUrl.getPort()).isEqualTo(443);
        assertThat(httpsUrl.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(httpsUrl.getOriginal()).isEqualTo("https://four.days/");
    }

    @Test
    @DisplayName("path 를 지정해서 URL 객체를 생성할 수 있다.")
    void constructorTest_originalString_pathExists() {
        Protocol http = new Protocol(1, "HTTP");
        URL url = new URL("ABCDEFG", http, "http://four.days/");

        assertThat(url.getKey()).isEqualTo("ABCDEFG");
        assertThat(url.getProtocol()).isEqualTo(http);
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(80);
        assertThat(url.getPath().orElseGet(() -> null)).isEqualTo("/");
        assertThat(url.getOriginal()).isEqualTo("http://four.days/");
    }

    @Test
    @DisplayName("path 가 없이 URL 객체를 생성할 수 있다.")
    void constructorTest_originalString_pathNotExists() {
        Protocol http = new Protocol(1, "HTTP");
        URL url = new URL("ABCDEFG", http, "http://four.days");

        assertThat(url.getProtocol()).isEqualTo(http);
        assertThat(url.getDomain()).isEqualTo("four.days");
        assertThat(url.getPort()).isEqualTo(80);
        assertThat(url.getPath()).isEmpty();
        assertThat(url.getOriginal()).isEqualTo("http://four.days");
        assertThat(url.getKey()).isEqualTo("ABCDEFG");
    }

    @Test
    @DisplayName("protocol 없이 URL 객체를 생성할 수 없다.")
    void constructorTest_originalString_protocolIsNull() {
        assertThatThrownBy(() -> new URL("ABCDEFG", null, "://four.days:-1/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is null.");
    }

    @Test
    @DisplayName("http 또는 https protocol 로만 URL 객체를 생성할 수 있다.")
    void constructorTest_originalString_protocolIsInvalid() {
        Protocol ftp = new Protocol(1, "ftp");
        assertThatThrownBy(() -> new URL("ABCDEFG", ftp, "ftp://four.days:80/"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("protocol is invalid. protocol=" + ftp);
    }

    @Test
    @DisplayName("domain 주소 없이 URL 객체를 생성할 수 없다.")
    void constructorTest_originalString_domainIsNull() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> new URL("ABCDEFG", http, "http://"))
                .isInstanceOf(InvalidUrlException.class)
                .hasMessage("url is invalid. url=" + "http://");
    }

    @Test
    @DisplayName("port 를 입력할 경우 양수만 입력할 수 있다.")
    void constructorTest_originalString_portIsNegative() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> new URL("ABCDEFG", http, "http://four.days:-1/"))
                .isInstanceOf(InvalidPortException.class)
                .hasMessage("port is invalid. port=" + -1);
    }

    @Test
    @DisplayName("key 없이 URL 객체를 생성할 수 없다.")
    void constructorTest_parameter_keyIsNull() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> URL.builder()
                .key(null)
                .protocol(http)
                .domain("four.days")
                .port(443)
                .path("/")
                .build())
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=" + null);
    }

    @Test
    @DisplayName("key 없이 URL 객체를 생성할 수 없다.")
    void constructorTest_originalString_keyIsNull() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> new URL(null, http, "https://four.days:443/hello"))
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=" + null);
    }

    @Test
    @DisplayName("key 는 '/' 문자를 포함할 수 없다.")
    void constructorTest_parameter_keyContainsSlash() {
        Protocol https = new Protocol(1, "HTTPS");
        assertThatThrownBy(() -> URL.builder()
                .key("ABCD/EFG")
                .protocol(https)
                .domain("four.days")
                .port(443)
                .path("/")
                .build())
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=ABCD/EFG");
    }

    @Test
    @DisplayName("key 는 '/' 문자를 포함할 수 없다.")
    void constructorTest_originalString_keyContainsSlash() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> new URL("ABCD/EFG", http, "https://four.days:443/hello"))
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=ABCD/EFG");
    }

    @Test
    @DisplayName("key 는 '+' 문자를 포함할 수 없다.")
    void constructorTest_parameter_keyContainsPlus() {
        Protocol https = new Protocol(1, "HTTPS");
        assertThatThrownBy(() -> URL.builder()
                .key("ABCD+EFG")
                .protocol(https)
                .domain("four.days")
                .port(443)
                .path("/")
                .build())
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=ABCD+EFG");
    }

    @Test
    @DisplayName("key 는 '+' 문자를 포함할 수 없다.")
    void constructorTest_originalString_keyContainsPlus() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> new URL("ABCD+EFG", http, "https://four.days:443/hello"))
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=ABCD+EFG");
    }

    @Test
    @DisplayName("key 는 '=' 문자를 포함할 수 없다.")
    void constructorTest_parameter_keyContainsEqual() {
        Protocol https = new Protocol(1, "HTTPS");
        assertThatThrownBy(() -> URL.builder()
                .key("ABCD=EFG")
                .protocol(https)
                .domain("four.days")
                .port(443)
                .path("/")
                .build())
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=ABCD=EFG");
    }

    @Test
    @DisplayName("key 는 '=' 문자를 포함할 수 없다.")
    void constructorTest_originalString_keyContainsEqual() {
        Protocol http = new Protocol(1, "HTTP");
        assertThatThrownBy(() -> new URL("ABCD=EFG", http, "https://four.days:443/hello"))
                .isInstanceOf(InvalidKeyException.class)
                .hasMessage("key is invalid. key=ABCD=EFG");
    }
}