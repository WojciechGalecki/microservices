package wg.microservices.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import wg.microservices.clients.HealthClient;

@Configuration
public class HealthCheckConfiguration {

    @Autowired
    HealthClient healthClient;

    @Bean
    ReactiveHealthContributor coreServices() {
        final Map<String, ReactiveHealthIndicator> registry = new LinkedHashMap<>();

        registry.put("product", () -> healthClient.getProductHealth());
        registry.put("recommendation", () -> healthClient.getRecommendationHealth());
        registry.put("review", () -> healthClient.getReviewHealth());

        return CompositeReactiveHealthContributor.fromMap(registry);
    }
}
