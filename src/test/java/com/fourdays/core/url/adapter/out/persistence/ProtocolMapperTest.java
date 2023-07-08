package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.common.config.MyBatisConfig;
import com.fourdays.core.url.adapter.out.persistence.dto.ProtocolDto;
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

    @Test
    @DisplayName("프로토콜을 저장하고, name으로 프토토콜을 조회할 수 있다.")
    void saveTest() {
        ProtocolDto protocolDto = ProtocolDto.builder()
                .name("FTP")
                .build();
        protocolMapper.save(protocolDto);

        ProtocolDto findProtocolDto = protocolMapper.findByName("FTP");

        assertThat(findProtocolDto.getSeq()).isNotNull();
        assertThat(findProtocolDto.getName()).isEqualTo("FTP");
    }

    @Test
    @DisplayName("seq로 프로토콜을 조회할 수 있다.")
    void findBySeqTest() {
        ProtocolDto protocolDto = ProtocolDto.builder()
                .name("FTP")
                .build();
        protocolMapper.save(protocolDto);

        ProtocolDto findProtocolDto = protocolMapper.findByName("FTP");
        int seq = findProtocolDto.getSeq();
        ProtocolDto findBySeq = protocolMapper.findBySeq(seq);

        assertThat(findProtocolDto).isEqualTo(findBySeq);
    }
}