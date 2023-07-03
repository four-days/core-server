package com.fourdays.core.domain.url.service;

public interface UrlService {

    String shortenUrl(String url);

    String findOriginalUrlByKey(String key);
}
