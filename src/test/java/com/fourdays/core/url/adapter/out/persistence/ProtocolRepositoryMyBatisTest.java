package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.common.config.MyBatisConfig;
import com.fourdays.core.url.application.port.out.ProtocolRepository;
import com.fourdays.core.url.config.ProtocolConfig;
import com.fourdays.core.url.domain.Protocol;
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
@ContextConfiguration(classes = {MyBatisConfig.class, ProtocolConfig.class})
class ProtocolRepositoryMyBatisTest {

    @Autowired
    private ProtocolRepository protocolRepository;

    @Test
    @DisplayName("프로토콜을 생성하고, 프로토콜명으로 프로토콜을 조회할 수 있다.")
    void saveTest() {
        Protocol newProtocol = Protocol.builder()
                .name("FTP")
                .build();

        protocolRepository.save(newProtocol);
        Optional<Protocol> optionalProtocol = protocolRepository.findByName("FTP");

        assertThat(optionalProtocol).isNotEmpty();
        assertThat(optionalProtocol.get().getName()).isEqualTo(newProtocol.getName());
    }

    @Test
    @DisplayName("존재하지 않는 프로토콜명으로 조회하면 빈 Optional이 반환된다.")
    void emptyTest() {
        Optional<Protocol> optionalProtocol = protocolRepository.findByName("None");

        assertThat(optionalProtocol).isEmpty();
    }
}