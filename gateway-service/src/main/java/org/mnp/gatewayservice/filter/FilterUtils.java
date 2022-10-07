package org.mnp.gatewayservice.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

public class FilterUtils {
    public static final String CORRELATION_ID = "correlation-id";

    public static String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CORRELATION_ID) != null) {
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
            return requestHeaderList.stream().findFirst().get();
        } else {
            return null;
        }
    }

    public static ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public static ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

    public static boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (getCorrelationId(requestHeaders) != null) {
            return true;
        } else {
            return false;
        }
    }

    public static String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
