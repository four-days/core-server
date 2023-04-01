package com.fourdays.core.domain.url.model.repository;

import com.fourdays.core.domain.url.model.entity.URL;

import java.util.Optional;

public interface UrlRepository {

    URL save(URL url);

    Optional<URL> findByUrlKey(String urlKey);
}
