package com.sparkplug.auth.infrastructure.security.custom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class Http403CustomEntryPoint implements AuthenticationEntryPoint {
    private static final Log logger = LogFactory.getLog(Http403CustomEntryPoint.class);

    public Http403CustomEntryPoint() {
    }

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        logger.debug("Pre-authenticated entry point called. Rejecting access");

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = String.format(
                "{\"timestamp\":\"%s\", \"status\":403, \"error\":\"Forbidden\", \"message\":\"%s\", \"path\":\"%s\"}",
                java.time.Instant.now(),
                authException.getMessage(),
                request.getRequestURI()
        );

        response.getWriter().write(json);

    }
}
