package com.fourdays.core.domain.url.model.service;

import com.fourdays.core.domain.url.model.entity.Protocol;
import com.fourdays.core.domain.url.model.entity.URL;
import com.fourdays.core.domain.url.model.entity.exception.InvalidProtocolException;
import com.fourdays.core.domain.url.model.repository.ProtocolRepository;
import com.fourdays.core.domain.url.model.repository.UrlRepository;
import com.fourdays.core.domain.url.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final Base62Encoder base62Encoder;

    private final UrlRepository urlRepository;

    private final ProtocolRepository protocolRepository;

    @Override
    public String shortenUrl(String originalUrl) {
        String key = base62Encoder.encode();
        Protocol protocol = getProtocol(originalUrl);
        return urlRepository.save(new URL(key, protocol, originalUrl))
                .getKey();
    }

    @Override
    public String findOriginalUrlByKey(String key) {
        URL findUrl = urlRepository.findByKey(key)
                .orElse(null);

        return findUrl == null ? null : findUrl.getOriginal();
    }

    private Protocol getProtocol(String url) {
        String protocolName = parseProtocol(url);
        return protocolRepository.findByName(protocolName)
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
