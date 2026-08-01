package com.alacriti.merchant.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {

            long timeTaken = System.currentTimeMillis() - startTime;

            log.info(
                    "CorrelationId={} Method={} URI={} Status={} TimeTaken={} ms",
                    MDC.get("X-Correlation-ID"),
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    timeTaken
            );
        }
    }
}