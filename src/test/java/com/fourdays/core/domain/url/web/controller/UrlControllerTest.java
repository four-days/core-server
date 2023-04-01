package com.fourdays.core.domain.url.web.controller;

import com.fourdays.core.common.response.BasicResponse;
import com.fourdays.core.domain.url.model.service.UrlService;
import com.fourdays.core.domain.url.web.controller.dto.request.ShortenUrlDto;
import com.fourdays.core.domain.url.web.controller.dto.response.UrlKeyDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    MockMvc mvc;

    @Autowired
    WebApplicationContext context;

    @MockBean
    UrlService urlService;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    @DisplayName("shortenUrl > urlKeyCheck")
    void shortenUrlTest_success() {
        UrlController urlController = new UrlController(url -> "urlKey");
        BasicResponse<UrlKeyDto> basicResponse = urlController.shortenUrl(new ShortenUrlDto("https://four.days/"));
        UrlKeyDto urlKeyDto = basicResponse.getData();
        String urlKey = urlKeyDto.getUrlKey();
        Assertions.assertThat(urlKey).isEqualTo("urlKey");
    }

    @Test
    @DisplayName("shortenUrl > api test > success")
    void shortenUrlTest_apiTest_success() throws Exception {
        Mockito.when(urlService.shortenUrl("https://four.days/")).thenReturn("urlKey");
        mvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"url\" : \"https://four.days/\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.urlKey").value("urlKey"));
    }

    @Test
    @DisplayName("shortenUrl > invalid url 1")
    void shortenUrlTest_invalidUrl1() throws Exception {
        mvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"url\" : \"htt://four.days/\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.fieldError.field").value("url"))
                .andExpect(jsonPath("$.data.fieldError.message").value("invalid url"));
    }

    @Test
    @DisplayName("shortenUrl > invalid url 2")
    void shortenUrlTest_invalidUrl2() throws Exception {
        mvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"url\" : \"http://days/\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.fieldError.field").value("url"))
                .andExpect(jsonPath("$.data.fieldError.message").value("invalid url"));
    }

    @Test
    @DisplayName("shortenUrl > invalid url 3")
    void shortenUrlTest_invalidUrl3() throws Exception {
        mvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"url\" : \"http:/days/\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.fieldError.field").value("url"))
                .andExpect(jsonPath("$.data.fieldError.message").value("invalid url"));
    }

    @Test
    @DisplayName("shortenUrl > invalid url 4")
    void shortenUrlTest_invalidUrl4() throws Exception {
        mvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"url\" : \"http//days/\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.fieldError.field").value("url"))
                .andExpect(jsonPath("$.data.fieldError.message").value("invalid url"));
    }

    @Test
    @DisplayName("shortenUrl > invalid url 5")
    void shortenUrlTest_invalidUrl5() throws Exception {
        mvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"url\" : \"http://\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.fieldError.field").value("url"))
                .andExpect(jsonPath("$.data.fieldError.message").value("invalid url"));
    }

    @Test
    @DisplayName("shortenUrl > invalid url 6")
    void shortenUrlTest_invalidUrl6() throws Exception {
        mvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"url\" : \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.fieldError.field").value("url"))
                .andExpect(jsonPath("$.data.fieldError.message").value("invalid url"));
    }

    @Test
    @DisplayName("shortenUrl > invalid url 7")
    void shortenUrlTest_invalidUrl7() throws Exception {
        mvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"url\" : null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.fieldError.field").value("url"))
                .andExpect(jsonPath("$.data.fieldError.message").value("invalid url"));
    }
}