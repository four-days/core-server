package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.common.config.MyBatisConfig;
import com.fourdays.core.url.adapter.out.persistence.dto.ProtocolDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {MyBatisConfig.class})
class ProtocolMapperTest {

    @Autowired
    private ProtocolMapper protocolMapper;

    @BeforeEach
    void setUp() {
        protocolMapper.removeAll();
    }

    @Test
    @DisplayName("프로토콜을 저장하고, name으로 프토토콜을 조회할 수 있다.")
    void saveTest() {
        // given
        ProtocolDto protocolDto = ProtocolDto.builder()
                .name("HTTP")
                .build();
        protocolMapper.save(protocolDto);

        // when
        ProtocolDto findProtocolDto = protocolMapper.findByName("HTTP");

        // then
        assertThat(findProtocolDto.getSeq()).isNotNull();
        assertThat(findProtocolDto.getName()).isEqualTo("HTTP");
    }

    @Test
    @DisplayName("seq로 프로토콜을 조회할 수 있다.")
    void findBySeqTest() {
        // given
        ProtocolDto protocolDto = ProtocolDto.builder()
                .name("HTTP")
                .build();
        protocolMapper.save(protocolDto);
        ProtocolDto findProtocolDto = protocolMapper.findByName("HTTP");
        int seq = findProtocolDto.getSeq();

        // when
        ProtocolDto findBySeq = protocolMapper.findBySeq(seq);

        // then
        assertThat(findProtocolDto).isEqualTo(findBySeq);
    }
}