package com.allitov.hotelapi.security;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.web.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implementation of AccessDeniedHandler.
 * @author allitov
 */
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse responseBody = new ErrorResponse(ExceptionMessage.ACCESS_DENIED);
        OutputStream responseStream = response.getOutputStream();
        objectMapper.writeValue(responseStream, responseBody);
        responseStream.flush();
    }
}
