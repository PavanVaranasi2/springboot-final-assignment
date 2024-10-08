package com.pavan.hotel_service.config;

import com.pavan.hotel_service.client.RoomClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    private final LoadBalancedExchangeFilterFunction filterFunction;

    @Autowired
    public WebClientConfig(LoadBalancedExchangeFilterFunction filterFunction) {
        this.filterFunction = filterFunction;
    }

    @Bean
    public WebClient roomWebClient()
    {
        return WebClient.builder()
                .baseUrl("http://room-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public RoomClient roomClient()
    {
        HttpServiceProxyFactory httpServiceProxyFactory=HttpServiceProxyFactory.
                builder(WebClientAdapter.forClient(roomWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(RoomClient.class);
    }
}
