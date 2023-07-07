package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.url.application.port.out.UrlRepository;
import com.fourdays.core.url.domain.URL;
import com.fourdays.core.url.exception.InvalidUrlKeyException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UrlRepositoryStub implements UrlRepository {

    private final Map<String, URL> store;

    public UrlRepositoryStub() {
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
