package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.common.config.MyBatisConfig;
import com.fourdays.core.url.application.port.out.ProtocolRepository;
import com.fourdays.core.url.application.port.out.UrlRepository;
import com.fourdays.core.url.config.ProtocolConfig;
import com.fourdays.core.url.config.UrlConfig;
import com.fourdays.core.url.domain.Protocol;
import com.fourdays.core.url.domain.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {MyBatisConfig.class, UrlConfig.class, ProtocolConfig.class})
class UrlRepositoryMyBatisTest {

    @Autowired
    private UrlMapper urlMapper;

    @Autowired
    private ProtocolMapper protocolMapper;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ProtocolRepository protocolRepository;

    @BeforeEach
    void setUp() {
        urlMapper.removeAll();
        protocolMapper.removeAll();
    }

    @Test
    @DisplayName("URL을 생성하고, urlKey로 URL을 조회할 수 있다.")
    void saveTest() {
        // given
        Protocol newProtocol = Protocol.builder()
                .name("HTTP")
                .build();
        Protocol savedProtocol = protocolRepository.save(newProtocol);
        URL newUrl = new URL("urlKey", savedProtocol, "four.days", null, "/");
        URL savedUrl = urlRepository.save(newUrl);

        // when
        Optional<URL> optionalUrl = urlRepository.findByUrlKey("urlKey");

        // then
        assertThat(optionalUrl).isNotEmpty();
        assertThat(optionalUrl.get()).isEqualTo(savedUrl);
    }

    @Test
    @DisplayName("존재하지 않는 urlKey로 URL을 조회하면 빈 Optional이 반환된다.")
    void emptyTest() {
        // when
        Optional<URL> optionalUrl = urlRepository.findByUrlKey("None");

        // then
        assertThat(optionalUrl).isEmpty();
    }
}