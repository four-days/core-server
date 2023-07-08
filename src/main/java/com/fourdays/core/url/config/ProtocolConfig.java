package com.fourdays.core.url.config;

import com.fourdays.core.url.adapter.out.persistence.ProtocolMapper;
import com.fourdays.core.url.application.port.out.ProtocolRepository;
import com.fourdays.core.url.adapter.out.persistence.ProtocolRepositoryMyBatis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProtocolConfig {

    @Bean
    public ProtocolRepository protocolRepository(ProtocolMapper protocolMapper) {
        return new ProtocolRepositoryMyBatis(protocolMapper);
    }
}
