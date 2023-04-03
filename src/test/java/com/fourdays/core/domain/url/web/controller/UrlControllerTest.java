package com.fourdays.core.domain.url.web.controller;

import com.fourdays.core.domain.url.model.service.UrlService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    @DisplayName("findOriginalUrlByUrlKey > exists")
    void findOriginalUrlByUrlKeyTest_exists() throws Exception {
        Mockito.when(urlService.findOriginalUrlByUrlKey("urlKey12")).thenReturn("https://four.days/");
        mvc.perform(get("/api/v1/urls/urlKey12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.url").value("https://four.days/"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > not exists")
    void findOriginalUrlByUrlKeyTest_notExists() throws Exception {
        mvc.perform(get("/api/v1/urls/urlKey12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > length is 1")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_lengthIs1() throws Exception {
        mvc.perform(get("/api/v1/urls/1")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > length is 2")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_lengthIs2() throws Exception {
        mvc.perform(get("/api/v1/urls/12")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("12"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > length is 3")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_lengthIs3() throws Exception {
        mvc.perform(get("/api/v1/urls/123")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("123"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > length is 4")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_lengthIs4() throws Exception {
        mvc.perform(get("/api/v1/urls/1234")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > length is 5")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_lengthIs5() throws Exception {
        mvc.perform(get("/api/v1/urls/12345")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("12345"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > length is 6")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_lengthIs6() throws Exception {
        mvc.perform(get("/api/v1/urls/123456")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("123456"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > length is 7")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_lengthIs7() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > length is 9")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_lengthIs9() throws Exception {
        mvc.perform(get("/api/v1/urls/123456789")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("123456789"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > invalid word 1")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_invalidWord1() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567-")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567-"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > invalid word 2")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_invalidWord2() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567+")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567+"));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > invalid word 3")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_invalidWord3() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567=")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567="));
    }

    @Test
    @DisplayName("findOriginalUrlByUrlKey > constraintViolationExceptionException > invalid word 4")
    void findOriginalUrlByUrlKeyTest_constraintViolationExceptionException_invalidWord4() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567\\")
        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567\\"));
    }
}