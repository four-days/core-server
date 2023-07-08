package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.common.config.MyBatisConfig;
import com.fourdays.core.url.adapter.out.persistence.dto.UrlDto;
import com.fourdays.core.url.domain.Protocol;
import com.fourdays.core.url.domain.URL;
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
class UrlMapperTest {

    @Autowired
    private UrlMapper urlMapper;

    @Test
    @DisplayName("URL을 저장하고, seq로 URl을 조회할 수 있다.")
    void saveTest() {
        String urlKey = "key";
        Protocol protocol = new Protocol(1, "HTTP");
        String original = "http://four.days/";
        URL url = new URL(urlKey, protocol, original);
        UrlDto urlDto = UrlDto.of(url);

        urlMapper.save(urlDto);
        UrlDto findUrlDto = urlMapper.findByUrlKey("key");

        assertThat(findUrlDto.getSeq()).isNotNull();
        assertThat(findUrlDto.getUrlKey()).isEqualTo("key");
        assertThat(findUrlDto.getProtocolSeq()).isEqualTo(1);
        assertThat(findUrlDto.getDomain()).isEqualTo("four.days");
        assertThat(findUrlDto.getPort()).isEqualTo(80);
        assertThat(findUrlDto.getPath()).isEqualTo("/");
        assertThat(findUrlDto.getOriginal()).isEqualTo("http://four.days/");
    }
}