package com.algaworks.springrestcourse.service.delivery;

import com.algaworks.springrestcourse.model.StoreItem;
import org.springframework.stereotype.Component;

@Component
@DeliveryMethod(DeliveryType.WATER)
public class WaterDelivery implements Deliverer {

    @Override
    public void delivery(StoreItem storeItem) {
        System.out.println(
                "Delivering item: " + storeItem.getItemName() +
                        " from price: " + storeItem.getItemPrice() + " with ship."
        );
    }
}
