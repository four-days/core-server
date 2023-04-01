package com.fourdays.core.common.exception.handler;

import com.fourdays.core.common.response.ErrorResponse;
import com.fourdays.core.domain.url.model.entity.exception.InvalidException;
import com.fourdays.core.domain.url.model.entity.exception.InvalidPathException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class CommonRestControllerAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, Object> data = new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            Map<String, String> errorInfo = new HashMap<>();
            errorInfo.put("field", fieldError.getField());
            errorInfo.put("message", fieldError.getDefaultMessage());
            data.put("fieldError", errorInfo);
        }

        for (ObjectError globalError : bindingResult.getGlobalErrors()) {
            Map<String, String> errorInfo = new HashMap<>();
            errorInfo.put("field", globalError.getObjectName());
            errorInfo.put("message", globalError.getDefaultMessage());
            data.put("globalError", errorInfo);
        }

        return ErrorResponse.<Map<String, Object>>builder()
                .data(data)
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidException.class)
    public ErrorResponse<String> invalidExceptionHandler(InvalidPathException e) {
        return ErrorResponse.<String>builder()
                .data(e.getMessage())
                .build();
    }
}
