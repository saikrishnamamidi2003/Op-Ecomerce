package com.alacriti.merchant.util;

import org.slf4j.MDC;

public class CorrelationIdUtil {

    public static final String CORRELATION_ID = "X-Correlation-ID";

    private CorrelationIdUtil() {
    }

    public static String getCorrelationId() {
        return MDC.get(CORRELATION_ID);
    }
}