package wg.microservices.configuration;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wg.api.core.product.Product;
import wg.event.Event;
import wg.exception.EventProcessingException;
import wg.microservices.services.ProductService;

@Slf4j
@Configuration
@AllArgsConstructor
public class MessageProcessorConfig {
    private final ProductService productService;

    @Bean
    public Consumer<Event<Integer, Product>> messageProcessor() {
        return event -> {
            log.info("Process message created at {}...", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE:
                    Product product = event.getData();
                    log.info("Create product with ID: {}", product.productId());
                    productService.createProduct(product).block();
                    break;

                case DELETE:
                    int productId = event.getKey();
                    log.info("Delete product with ProductID: {}", productId);
                    productService.deleteProduct(productId).block();
                    break;

                default:
                    String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected a CREATE or DELETE event";
                    log.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
            }

            log.info("Message processing done!");
        };
    }
}
