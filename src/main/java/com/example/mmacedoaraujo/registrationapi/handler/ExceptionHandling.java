package com.example.mmacedoaraujo.registrationapi.handler;

import com.example.mmacedoaraujo.registrationapi.domain.HttpResponse;
import com.example.mmacedoaraujo.registrationapi.exceptions.UserNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(UserNotFoundExeption.class)
    public ResponseEntity<HttpResponse> userNotFoundExceptionHandle() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, "Nenhum usuário encontrado com o id fornecido! ");
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = HttpResponse.builder()
                .httpStatusCode(httpStatus.value())
                .reason(httpStatus.getReasonPhrase())
                .message(message)
                .httpStatus(httpStatus)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(httpResponse, httpStatus);
    }
}
