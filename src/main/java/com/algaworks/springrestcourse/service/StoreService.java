package com.algaworks.springrestcourse.service;

import com.algaworks.springrestcourse.model.StoreItem;
import com.algaworks.springrestcourse.service.delivery.Deliverer;
import com.algaworks.springrestcourse.service.delivery.DeliveryMethod;
import com.algaworks.springrestcourse.service.delivery.DeliveryType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StoreService {

    private final Deliverer deliverer;
    private final ApplicationEventPublisher eventPublisher;

    public StoreService(
            @DeliveryMethod(DeliveryType.AIR) Deliverer deliverer,
            ApplicationEventPublisher eventPublisher
    ) {
        this.deliverer = deliverer;
        this.eventPublisher = eventPublisher;
    }

    public void buyItem(StoreItem storeItem) {
        eventPublisher.publishEvent(storeItem);
        deliverer.delivery(storeItem);
    }
}
