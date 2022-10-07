package org.mnp.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayFiltersConfiguration {

    @Bean
    public GlobalFilter preCorrelationAddingFilter() {
        return (exchange, chain) -> {
            HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
            if (FilterUtils.isCorrelationIdPresent(requestHeaders)) {
                System.out.println("CorrelationId already exists for this request: " + FilterUtils.getCorrelationId(requestHeaders));
            } else {
                String correlationID = FilterUtils.generateCorrelationId();
                exchange = FilterUtils.setCorrelationId(exchange, correlationID);

                System.out.println("Assigned CorrelationId to the request: " + correlationID);
            }

            return chain.filter(exchange);
        };
    }

    @Bean
    public GlobalFilter postCorrelationFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = FilterUtils.getCorrelationId(requestHeaders);
                exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);

                System.out.println("Added the CorrelationId to the response headers: " + correlationId);
            }));
        };
    }
}
