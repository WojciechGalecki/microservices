package wg.microservices.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class Config {

    @Bean
    public Scheduler scheduler() {
        return Schedulers.single();
    }
}
