package com.fourdays.core.domain.url.repository;

import com.fourdays.core.domain.url.entity.URL;
import com.fourdays.core.domain.url.entity.exception.InvalidKeyException;
import org.springframework.stereotype.Repository;
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
        String key = url.getKey();
        if (StringUtils.hasText(key)) {
            store.put(key, url);
            return url;
        }

        throw new InvalidKeyException("key is invalid. key=" + key);
    }

    @Override
    public Optional<URL> findByKey(String key) {
        return Optional.ofNullable(store.get(key));
    }
}
