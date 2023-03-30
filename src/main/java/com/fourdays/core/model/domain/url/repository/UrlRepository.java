package com.fourdays.core.model.domain.url.repository;

import com.fourdays.core.model.domain.url.entity.URL;

import java.util.Optional;

public interface UrlRepository {

    URL save(URL url);

    Optional<URL> findByUrlKey(String urlKey);
}
