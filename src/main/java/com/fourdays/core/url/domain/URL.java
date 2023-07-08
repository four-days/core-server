package com.fourdays.core.url.domain;

import com.fourdays.core.url.exception.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
public class URL {

    private Long seq;

    private final String urlKey;

    private final Protocol protocol;

    private final String domain;

    private final Integer port;

    private final String path;

    private final String original;

    private static final Set<String> possibleProtocols = new HashSet<>(List.of("HTTP", "HTTPS"));


    @Builder
    public URL(String urlKey, Protocol protocol, String domain, Integer port, String path) {
        this.protocol = protocol;
        this.domain = domain;
        this.port = port != null ? port : getPortByProtocol();
        this.path = path;
        this.urlKey = urlKey;

        validateData();
        this.original = createOriginal();
    }

    @Builder(builderMethodName = "builderByDto")
    public URL(Long seq, String urlKey, Protocol protocol, String domain, Integer port, String path, String original) {
        if (seq == null) {
            throw new IllegalStateException("seq is null");
        }

        this.seq = seq;
        this.urlKey = urlKey;
        this.protocol = protocol;
        this.domain = domain;
        this.port = port;
        this.path = path;
        this.original = original;
        validateData();
    }

    public URL(String urlKey, Protocol protocol, String originalUrl) {
        this.urlKey = urlKey;
        this.protocol = protocol;

        String[] split = originalUrl.split("://");
        if (split.length != 2) {
            throw new InvalidUrlException("url is invalid. url=" + originalUrl);
        }

        int pathIndex = split[1].indexOf("/");

        String domainAndPort = pathIndex > 0 ? split[1].substring(0, pathIndex) : split[1];
        if (domainAndPort.contains(":")) {
            String[] tokens = domainAndPort.split(":");
            this.domain = tokens[0];
            this.port = Integer.parseInt(tokens[1]);
        } else {
            this.domain = domainAndPort;
            if (isProtocolHttp()) {
                this.port = 80;
            } else if (isProtocolHttps()) {
                this.port = 443;
            } else {
                this.port = 0;
            }
        }

        if (pathIndex > 0) {
            this.path = split[1].substring(pathIndex);
        } else {
            this.path = null;
        }

        this.original = originalUrl;
        validateData();
    }

    private void validateData() {
        validateProtocol();
        validateDomain();
        validatePort();
        validatePath();
        validateUrlKey();
    }

    private void validateProtocol() {
        if (this.protocol == null) {
            throw new InvalidProtocolException("protocol is null.");
        }

        if (!possibleProtocols.contains(this.protocol.getName().toUpperCase())) {
            throw new InvalidProtocolException("protocol is invalid. protocol=" + this.protocol);
        }
    }

    private void validateDomain() {
        if (StringUtils.hasText(this.domain)) {
            return;
        }

        throw new InvalidDomainException("domain is invalid. domain=" + this.domain);
    }

    private void validatePort() {
        if (this.port > 0) {
            return;
        }

        throw new InvalidPortException("port is invalid. port=" + this.port);
    }

    private void validatePath() {
        if (this.path == null || this.path.startsWith("/")) {
            return;
        }

        throw new InvalidPathException("path is invalid. path=" + this.path);
    }

    private void validateUrlKey() {
        if (StringUtils.hasText(this.urlKey) && !urlKey.contains("/") && !urlKey.contains("=") && !urlKey.contains("+")) {
            return;
        }

        throw new InvalidUrlKeyException("urlKey is invalid. urlKey=" + this.urlKey);
    }

    private String createOriginal() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(this.protocol.getName().toLowerCase());
        urlBuilder.append("://");
        urlBuilder.append(this.domain);
        urlBuilder.append(this.port != null ? ":" + this.port : "");
        urlBuilder.append(this.path == null ? "" : this.path);
        return urlBuilder.toString();
    }

    private Integer getPortByProtocol() {
        if (isProtocolHttp()) {
            return 80;
        } else if (isProtocolHttps()) {
            return 443;
        }

        throw new InvalidProtocolException("protocol is invalid. protocol=" + this.protocol);
    }

    private boolean isProtocolHttp() {
        return this.protocol.isHttp();
    }

    private boolean isProtocolHttps() {
        return this.protocol.isHttps();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return Objects.equals(getSeq(), url.getSeq()) && Objects.equals(getUrlKey(), url.getUrlKey()) && Objects.equals(getProtocol(), url.getProtocol()) && Objects.equals(getDomain(), url.getDomain()) && Objects.equals(getPort(), url.getPort()) && Objects.equals(getPath(), url.getPath()) && Objects.equals(getOriginal(), url.getOriginal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeq(), getUrlKey(), getProtocol(), getDomain(), getPort(), getPath(), getOriginal());
    }

    @Override
    public String toString() {
        return "URL{" +
                "seq=" + seq +
                ", urlKey='" + urlKey + '\'' +
                ", protocol=" + protocol +
                ", domain='" + domain + '\'' +
                ", port=" + port +
                ", path='" + path + '\'' +
                ", original='" + original + '\'' +
                '}';
    }
}
