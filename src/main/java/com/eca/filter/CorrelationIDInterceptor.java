package com.eca.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


@Configuration
@Order(-1)
public class CorrelationIDInterceptor implements Filter {

    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";
    private static final String CORRELATION_ID_HEADER_NAME = "Correlation-Id";


    private String getCorrelationIdFromHeader(final HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER_NAME);
        if (StringUtils.isBlank(correlationId) || StringUtils.isEmpty(correlationId)) {
            correlationId = generateUniqueCorrelationId();
        }
        return correlationId;
    }
   
    private String generateUniqueCorrelationId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       HttpServletRequest request = (HttpServletRequest) servletRequest;
       final String correlationId = getCorrelationIdFromHeader(request);
        ThreadContext.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
        filterChain.doFilter(request, servletResponse);
        this.resetThreadContext();
    }

    private void resetThreadContext() {
        ThreadContext.remove(CORRELATION_ID_LOG_VAR_NAME);
    }
}