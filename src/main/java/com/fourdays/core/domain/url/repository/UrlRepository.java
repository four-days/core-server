package com.fourdays.core.domain.url.repository;

import com.fourdays.core.domain.url.entity.URL;

import java.util.Optional;

public interface UrlRepository {

    URL save(URL url);

    Optional<URL> findByUrlKey(String key);
}
