package wg.event;

import java.time.LocalDateTime;

public class Event<K, T> {

    public enum Type {
        CREATE,
        DELETE
    }

    private final Type eventType;
    private final K key;
    private final T data;
    private final String eventCreatedAt;

    public Event() {
        this.eventType = null;
        this.key = null;
        this.data = null;
        this.eventCreatedAt = null;
    }

    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = LocalDateTime.now().toString();
    }

    public Type getEventType() {
        return eventType;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }


    public String getEventCreatedAt() {
        return eventCreatedAt;
    }
}
