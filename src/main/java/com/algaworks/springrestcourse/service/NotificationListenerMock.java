package com.algaworks.springrestcourse.service;

import com.algaworks.springrestcourse.configuration.NotifierProperties;
import com.algaworks.springrestcourse.model.StoreItem;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.event.EventListener;

public class NotificationListenerMock {

    private final NotifierProperties properties;

    public NotificationListenerMock(NotifierProperties properties) {
        this.properties = properties;
    }

    @EventListener
    public void consumeStoreItemEvent(StoreItem storeItem) {
        System.out.println("Mocking Notifying about the bought of item: " + storeItem.toString()
        + " server: " + properties.getServerHost() + " port: " + properties.getServerPort());
    }

    @PostConstruct
    void init() {
        System.out.println("Initializing listener: " + this);
    }

    @PreDestroy
    void destroy() {
        System.out.println("Destroying listener: " + this);
    }
}
