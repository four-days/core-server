package com.fourdays.core.url.adapter.in.web;

import com.fourdays.core.url.application.port.in.UrlService;
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
    @DisplayName("url 을 전달하면 url 이 단축된 urlKey 값이 반환된다.")
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
    @DisplayName("http 또는 https protocol 을 사용해야 한다.")
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
    @DisplayName("유효한 domain 주소를 전달해야 한다.")
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
    @DisplayName("유효한 url 을 전달해야 한다. '://'")
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
    @DisplayName("유효한 url 을 전달해야 한다. ':'")
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
    @DisplayName("domain 주소를 전달해야 한다.")
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
    @DisplayName("빈 문자열이 아닌 url 을 전달해야 한다.")
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
    @DisplayName("url 을 전달해야 한다.")
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
    @DisplayName("urlKey 로 원본 url 을 조회할 수 있다.")
    void findOriginalUrlByKeyTest_exists() throws Exception {
        Mockito.when(urlService.findOriginalUrlByUrlKey("urlKey12")).thenReturn("https://four.days/");
        mvc.perform(get("/api/v1/urls/urlKey12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.url").value("https://four.days/"));
    }

    @Test
    @DisplayName("유효하지 않은 urlKey 로는 원본 url 을 조회할 수 없다.")
    void findOriginalUrlByKeyTest_notExists() throws Exception {
        mvc.perform(get("/api/v1/urls/urlKey12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("8자리의 urlKey 를 전달해야 한다. urlKey 길이 = 1")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_lengthIs1() throws Exception {
        mvc.perform(get("/api/v1/urls/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1"));
    }

    @Test
    @DisplayName("8자리의 urlKey 를 전달해야 한다. urlKey 길이 = 2")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_lengthIs2() throws Exception {
        mvc.perform(get("/api/v1/urls/12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("12"));
    }

    @Test
    @DisplayName("8자리의 urlKey 를 전달해야 한다. urlKey 길이 = 3")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_lengthIs3() throws Exception {
        mvc.perform(get("/api/v1/urls/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("123"));
    }

    @Test
    @DisplayName("8자리의 urlKey 를 전달해야 한다. urlKey 길이 = 4")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_lengthIs4() throws Exception {
        mvc.perform(get("/api/v1/urls/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234"));
    }

    @Test
    @DisplayName("8자리의 urlKey 를 전달해야 한다. urlKey 길이 = 5")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_lengthIs5() throws Exception {
        mvc.perform(get("/api/v1/urls/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("12345"));
    }

    @Test
    @DisplayName("8자리의 urlKey 를 전달해야 한다. urlKey 길이 = 6")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_lengthIs6() throws Exception {
        mvc.perform(get("/api/v1/urls/123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("123456"));
    }

    @Test
    @DisplayName("8자리의 urlKey 를 전달해야 한다. urlKey 길이 = 7")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_lengthIs7() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567"));
    }

    @Test
    @DisplayName("8자리의 urlKey 를 전달해야 한다. urlKey 길이 = 9")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_lengthIs9() throws Exception {
        mvc.perform(get("/api/v1/urls/123456789")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("123456789"));
    }

    @Test
    @DisplayName("urlKey 는 알파벳, 숫자만 입력해야 한다. '-'")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_invalidWord1() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567-")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567-"));
    }

    @Test
    @DisplayName("urlKey 는 알파벳, 숫자만 입력해야 한다. '+'")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_invalidWord2() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567+")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567+"));
    }

    @Test
    @DisplayName("urlKey 는 알파벳, 숫자만 입력해야 한다. '='")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_invalidWord3() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567="));
    }

    @Test
    @DisplayName("urlKey 는 알파벳, 숫자만 입력해야 한다. '\\'")
    void findOriginalUrlByKeyTest_constraintViolationExceptionException_invalidWord4() throws Exception {
        mvc.perform(get("/api/v1/urls/1234567\\")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data[0].message").value("invalid urlKey"))
                .andExpect(jsonPath("$.data[0].invalidValue").value("1234567\\"));
    }
}