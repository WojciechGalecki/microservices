package wg.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class RecommendationApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RecommendationApplication.class, args);
        String mongoDbHost = ctx.getEnvironment().getProperty("spring.data.mongodb.host");
        String mongoDbPort = ctx.getEnvironment().getProperty("spring.data.mongodb.port");
        log.info("Connected to MongoDB: {}:{}", mongoDbHost, mongoDbPort);
    }
}
