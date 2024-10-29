package wg.microservices.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Value("${app.threadPoolSize}") Integer threadPoolSize;
    @Value("${app.taskQueueSize}") Integer taskQueueSize;

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public Scheduler publishEventScheduler() {
        return Schedulers.newBoundedElastic(threadPoolSize, taskQueueSize, "publish-pool");
    }
}
