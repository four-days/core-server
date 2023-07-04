package com.fourdays.core.domain.url.repository;

import com.fourdays.core.domain.url.entity.URL;
import com.fourdays.core.domain.url.entity.exception.InvalidUrlKeyException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UrlRepositoryMemory implements UrlRepository {

    private final Map<String, URL> store;

    public UrlRepositoryMemory() {
        this.store = new HashMap<>();
    }

    @Override
    public URL save(URL url) {
        String key = url.getUrlKey();
        if (StringUtils.hasText(key)) {
            store.put(key, url);
            return url;
        }

        throw new InvalidUrlKeyException("urlKey is invalid. urlKey=" + key);
    }

    @Override
    public Optional<URL> findByUrlKey(String key) {
        return Optional.ofNullable(store.get(key));
    }
}
