package wg.microservices.events;

import static wg.event.Event.Type.CREATE;
import static wg.event.Event.Type.DELETE;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import wg.api.core.product.Product;
import wg.event.Event;

@Slf4j
@Component
public class EventPublisher {
    private static final String PRODUCTS_BINDING_NAME = "products-out-0";

    private final Scheduler publishEventScheduler;
    private final StreamBridge streamBridge;

    public EventPublisher(@Qualifier("publishEventScheduler") Scheduler publishEventScheduler, StreamBridge streamBridge) {
        this.publishEventScheduler = publishEventScheduler;
        this.streamBridge = streamBridge;
    }

    public Mono<Product> createProduct(Product product) {
        return Mono.fromCallable(() -> {
            send(new Event<>(CREATE, product.productId(), product));
            return product;
        }).subscribeOn(publishEventScheduler);
    }

    public Mono<Void> deleteProduct(int productId) {
        return Mono.fromRunnable(() -> send(new Event<>(DELETE, productId, null)))
            .subscribeOn(publishEventScheduler)
            .then();
    }

    private void send(Event<Integer, Product> event) {
        log.info("Sending a {} message to {}", event.getEventType(), EventPublisher.PRODUCTS_BINDING_NAME);
        Message<Event<Integer, Product>> message = MessageBuilder.withPayload(event)
            .setHeader("partitionKey", event.getKey())
            .build();
        streamBridge.send(EventPublisher.PRODUCTS_BINDING_NAME, message);
    }
}
