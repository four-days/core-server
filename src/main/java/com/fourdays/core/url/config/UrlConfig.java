package com.fourdays.core.url.config;

import com.fourdays.core.url.adapter.out.persistence.ProtocolMapper;
import com.fourdays.core.url.adapter.out.persistence.UrlMapper;
import com.fourdays.core.url.application.port.out.UrlRepository;
import com.fourdays.core.url.adapter.out.persistence.UrlRepositoryMyBatis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlConfig {

    @Bean
    public UrlRepository urlRepository(UrlMapper urlMapper, ProtocolMapper protocolMapper) {
        return new UrlRepositoryMyBatis(urlMapper, protocolMapper);
    }
}
