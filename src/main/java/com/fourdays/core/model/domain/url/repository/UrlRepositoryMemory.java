package com.fourdays.core.model.domain.url.repository;

import com.fourdays.core.model.domain.url.entity.URL;
import com.fourdays.core.model.domain.url.entity.exception.InvalidUrlKeyException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UrlRepositoryMemory implements UrlRepository {

    private static final Map<String, URL> store = new HashMap<>();

    @Override
    public URL save(URL url) {
        String urlKey = url.getUrlKey();
        if (StringUtils.hasText(urlKey)) {
            store.put(urlKey, url);
            return url;
        }

        throw new InvalidUrlKeyException("urlKey is invalid. urlKey=" + urlKey);
    }

    @Override
    public Optional<URL> findByUrlKey(String urlKey) {
        return Optional.ofNullable(store.get(urlKey));
    }
}
