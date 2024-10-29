package wg.gateway.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import wg.gateway.health.HealthcheckClient;

@Configuration
public class HealthCheckConfig {

    @Autowired
    HealthcheckClient healthcheckClient;

    @Bean
    ReactiveHealthContributor coreServices() {
        final Map<String, ReactiveHealthIndicator> registry = new LinkedHashMap<>();

        registry.put("product", () -> healthcheckClient.getProductHealth());
        registry.put("recommendation", () -> healthcheckClient.getRecommendationHealth());
        registry.put("review", () -> healthcheckClient.getReviewHealth());

        return CompositeReactiveHealthContributor.fromMap(registry);
    }
}
