package com.cnrmall.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author David
 * @date 2022/12/21 10:48
 *  Global Filters  ( https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#global-filters  )
 * 全局过滤器作用所有的路由，无需配置。 可以实现对权限的统一校验，安全验证等功能
 * 内置全局过滤器 ( 10 个全局过滤器)
 *
 *   The Gateway Metrics Filter
 *   The Local Response Cache Filter
 *   Forward Routing Filter
 *   The Netty Routing Filter
 *   The Netty Write Response Filter
 *   The ReactiveLoadBalancerClientFilter
 *   The RouteToRequestUrl Filter
 *   The Websocket Routing Filter
 *   Marking An Exchange As Routed *
 *
 *  自定义全局过滤器需要实现GlobalFilter 和 Ordered 接口
 */
@Component
@Slf4j
public class MyAuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //统一认证的demo
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if( !StringUtils.equals( "admin", token )){   //自动判断null
                //认证失败
                log.info("************MyAuthGlobalFilter***********: 认证失败.....");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED) ;
                return  exchange.getResponse().setComplete() ;
        }
        //继续向下传递
        return chain.filter(exchange);
    }

    /**
     *  值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
