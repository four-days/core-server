package com.fourdays.core.domain;

import com.fourdays.core.domain.exception.InvalidDomainException;
import com.fourdays.core.domain.exception.InvalidPathException;
import com.fourdays.core.domain.exception.InvalidPortException;
import com.fourdays.core.domain.exception.InvalidProtocolException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Getter
public class URL {

    private final String protocol;

    private final String domain;

    private final Integer port;

    private final Optional<String> path;

    private final String original;

    @Builder
    public URL(String protocol, String domain, Integer port, String path) {
        this.protocol = protocol;
        this.domain = domain;
        this.port = port != null ? port : getPortByProtocol();
        this.path = Optional.ofNullable(path);

        validateData();
        this.original = createOriginal(protocol, domain, port, path);
    }

    public URL(String original) {
        String[] protocolAndRest = original.split("://");
        this.protocol = protocolAndRest[0];

        if (protocolAndRest.length == 1) {
            throw new InvalidDomainException("domain is empty");
        }

        int pathIndex = protocolAndRest[1].indexOf("/");

        String domainAndPort = pathIndex > 0 ? protocolAndRest[1].substring(0, pathIndex) : protocolAndRest[1];
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
            this.path = Optional.of(protocolAndRest[1].substring(pathIndex));
        } else {
            this.path = Optional.empty();
        }

        validateData();
        this.original = original;
    }

    private void validateData() {
        validateProtocol();
        validateDomain();
        validatePort();
        validatePath();
    }

    private void validateProtocol() {
        if ((isProtocolHttp() || isProtocolHttps())) {
            return;
        }

        throw new InvalidProtocolException("protocol is invalid. protocol=" + this.protocol);
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
        if (this.path.isEmpty() || this.path.get().startsWith("/")) {
            return;
        }

        throw new InvalidPathException("path is invalid. path=" + this.path.get());
    }

    private String createOriginal(String protocol, String domain, Integer port, String path) {
        return protocol +
                "://" +
                domain +
                (port != null ? ":" + port : "") +
                (path != null ? path : "");
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
        return "http".equals(this.protocol);
    }

    private boolean isProtocolHttps() {
        return "https".equals(this.protocol);
    }
}
