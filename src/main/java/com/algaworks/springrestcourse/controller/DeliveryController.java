package com.algaworks.springrestcourse.controller;

import com.algaworks.springrestcourse.model.StoreItem;
import com.algaworks.springrestcourse.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeliveryController {

    private final StoreService storeService;

    public DeliveryController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String deliveryPackage() {
        final StoreItem item = new StoreItem("Tenis", 500D);
        storeService.buyItem(item);
        return "Hello";
    }
}
