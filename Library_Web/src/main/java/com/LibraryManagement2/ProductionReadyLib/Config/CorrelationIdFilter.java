package com.LibraryManagement2.ProductionReadyLib.Config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    public static final String CORRELATION_ID_HEADER = AppConstants.CORRELATION_ID_HEADER;
    public static final String MDC_KEY = AppConstants.MDC_KEY;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        // Put into MDC
        MDC.put(MDC_KEY, correlationId);

        // Add to response header
        response.setHeader(CORRELATION_ID_HEADER, correlationId);

        try {
            // ---- REQUEST START LOG ----
            logger.info(String.format(
                    "Request started: method=%s, path=%s, correlationId=%s",
                    request.getMethod(), request.getRequestURI(), correlationId
            ));




            filterChain.doFilter(request, response);

        } finally {
            long latencyMs = System.currentTimeMillis() - startTime;

            // ---- REQUEST END LOG ----
            logger.info(String.format(
                    "Request completed: method=%s, path=%s, status=%s, latencyMs=%s, correlationId=%s",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    latencyMs,
                    correlationId
            ));

            // VERY IMPORTANT: clean MDC
            MDC.clear();
        }
    }
}
