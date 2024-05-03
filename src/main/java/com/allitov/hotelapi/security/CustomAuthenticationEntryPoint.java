package com.allitov.hotelapi.security;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.web.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implementation of AuthenticationEntryPoint.
 * @author allitov
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse responseBody = new ErrorResponse(ExceptionMessage.AUTHENTICATION_FAILURE);
        OutputStream responseStream = response.getOutputStream();
        objectMapper.writeValue(responseStream, responseBody);
        responseStream.flush();
    }
}
