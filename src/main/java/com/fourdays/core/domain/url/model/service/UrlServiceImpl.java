package com.fourdays.core.domain.url.model.service;

import com.fourdays.core.domain.url.model.entity.URL;
import com.fourdays.core.domain.url.model.repository.UrlRepository;
import com.fourdays.core.domain.url.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    private final Base62Encoder base62Encoder;

    @Override
    public String shortenUrl(String originalUrl) {
        String key = base62Encoder.encode();
        return urlRepository.save(new URL(key, originalUrl))
                .getKey();
    }

    @Override
    public String findOriginalUrlByKey(String key) {
        URL findUrl = urlRepository.findByKey(key)
                .orElse(null);

        return findUrl == null ? null : findUrl.getOriginal();
    }
}
