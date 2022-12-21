package com.cnrmall.springcloud.predicate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author David
 * @date 2022/12/21 11:12
 *
 * 自定义一个断言工厂
 * 1.  名字必须是 ： 配置+ RoutePredicateFactory
 *       参考： org.springframework.cloud.gateway.handler.predicate.AfterRoutePredicateFactory
 * 2.  必须继承  AbstractRoutePredicateFactory<配置类>
 *
 * 测试地址： http://localhost:9527/pay/query/1?token=admin&age=18
 */
@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config>  implements Ordered {

    //构造函数
    public AgeRoutePredicateFactory() {
        super(AgeRoutePredicateFactory.Config.class);
    }

    //读取配置文件中的参数，赋值到配置类中
    @Override
    public List<String> shortcutFieldOrder() {
        //位置顺序必须跟配置文件中的顺序对应
        return Arrays.asList( "minAge", "maxAge" ) ;
    }

    //断言逻辑
    @Override
    public Predicate<ServerWebExchange> apply(AgeRoutePredicateFactory.Config  config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                //1 接收前端 age 参数
                String ageStr = serverWebExchange.getRequest().getQueryParams().getFirst("age");
                if(StringUtils.isNotEmpty(ageStr)) {   //自动非空判断
                      int age =  Integer.parseInt( ageStr ) ;
                      if ( age > config.getMinAge() && age <  config.getMaxAge() ) {
                           //年龄在时间范围内
                          return true ;
                      }else{
                          return  false ;
                      }
                }
                return false;
            }
        };
    }

    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 定义配置类，用于接收配置文件中对应的参数  application.yml
     */
    @Data
    @NoArgsConstructor
    public static class Config{
          private int minAge ;  //最小年龄
          private int maxAge; //最大年龄
    }
}
