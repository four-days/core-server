package com.fourdays.core.url.application.port.out;

import com.fourdays.core.url.domain.URL;

import java.util.Optional;

public interface UrlRepository {

    URL save(URL url);

    Optional<URL> findByUrlKey(String key);
}
