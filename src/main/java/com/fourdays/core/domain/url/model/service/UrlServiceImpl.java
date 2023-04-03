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
        String urlKey = base62Encoder.encode();
        return urlRepository.save(new URL(urlKey, originalUrl))
                .getUrlKey();
    }

    @Override
    public String findOriginalUrlByUrlKey(String urlKey) {
        URL findUrl = urlRepository.findByUrlKey(urlKey)
                .orElse(null);

        return findUrl == null ? null : findUrl.getOriginal();
    }
}
