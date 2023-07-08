package com.fourdays.core.url.adapter.out.persistence.dto;

import com.fourdays.core.url.domain.Protocol;
import com.fourdays.core.url.domain.URL;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UrlDto {

    private Long seq;

    private final String urlKey;

    private final Integer protocolSeq;

    private final String domain;

    private final Integer port;

    private final String path;

    private final String original;

    @Builder
    public UrlDto(Long seq, String urlKey, Integer protocolSeq, String domain, Integer port, String path, String original) {
        this.seq = seq;
        this.urlKey = urlKey;
        this.protocolSeq = protocolSeq;
        this.domain = domain;
        this.port = port;
        this.path = path;
        this.original = original;
    }

    public URL toEntity(Protocol protocol) {
        return URL.builderByDto()
                .seq(seq)
                .urlKey(urlKey)
                .protocol(protocol)
                .domain(domain)
                .port(port)
                .path(path)
                .original(original)
                .build();
    }

    public static UrlDto of(URL url) {
        return UrlDto.builder()
                .urlKey(url.getUrlKey())
                .protocolSeq(url.getProtocol().getSeq())
                .domain(url.getDomain())
                .port(url.getPort())
                .path(url.getPath())
                .original(url.getOriginal())
                .build();
    }

    @Override
    public String toString() {
        return "UrlDto{" +
                "seq=" + seq +
                ", urlKey='" + urlKey + '\'' +
                ", protocolSeq=" + protocolSeq +
                ", domain='" + domain + '\'' +
                ", paht='" + path + '\'' +
                ", original='" + original + '\'' +
                '}';
    }
}
