package com.fourdays.core.url.application.port.in;

public interface UrlService {

    String shortenUrl(String url);

    String findOriginalUrlByUrlKey(String key);
}
