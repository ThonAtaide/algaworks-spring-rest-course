package com.algaworks.springrestcourse.configuration;

import com.algaworks.springrestcourse.service.NotificationListener;
import com.algaworks.springrestcourse.service.NotificationListenerMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StoreServiceConfig {

    @Bean
    @Profile("prod")
    public NotificationListener notificationListener(NotifierProperties notifierProperties) {
        return new NotificationListener(notifierProperties);
    }

    @Bean
    @Profile("dev")
    public NotificationListenerMock notificationListenerMock(NotifierProperties notifierProperties) {
        return new NotificationListenerMock(notifierProperties);
    }
}
