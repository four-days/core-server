package com.fourdays.core.domain.url.model.service;

public interface UrlService {

    String shortenUrl(String url);

    String findOriginalUrlByUrlKey(String urlKey);
}
