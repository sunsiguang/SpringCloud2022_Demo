package com.cnrmall.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author David
 * @date 2022/12/20 19:37
 */
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("guonei_1",
                        r -> r.path("/guonei")
                            .uri("http://news.baidu.com/guonei"))
                .build();
    }
}
