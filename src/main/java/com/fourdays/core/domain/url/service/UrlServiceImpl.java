package com.fourdays.core.domain.url.service;

import com.fourdays.core.domain.url.entity.Protocol;
import com.fourdays.core.domain.url.entity.URL;
import com.fourdays.core.domain.url.entity.exception.InvalidProtocolException;
import com.fourdays.core.domain.url.repository.UrlRepository;
import com.fourdays.core.domain.url.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final Base62Encoder base62Encoder;

    private final UrlRepository urlRepository;

    private final ProtocolService protocolService;

    @Override
    public String shortenUrl(String originalUrl) {
        String key = base62Encoder.encode();
        Protocol protocol = getProtocol(originalUrl);
        return urlRepository.save(new URL(key, protocol, originalUrl))
                .getUrlKey();
    }

    @Override
    public String findOriginalUrlByUrlKey(String key) {
        URL findUrl = urlRepository.findByUrlKey(key)
                .orElse(null);

        return findUrl == null ? null : findUrl.getOriginal();
    }

    private Protocol getProtocol(String url) {
        String protocolName = parseProtocol(url);
        return protocolService.findByName(protocolName)
                .orElseThrow(() -> new InvalidProtocolException("protocol does not exist. parsed protocol name=" + protocolName));
    }

    private String parseProtocol(String url) {
        String protocolName = url.split("://")[0];
        if ("".equals(protocolName)) {
            throw new InvalidProtocolException("protocol name is invalid. protocol name=" + protocolName);
        }

        return protocolName;
    }
}
