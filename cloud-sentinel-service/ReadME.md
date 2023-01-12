###     Spring Cloud Alibaba Sentinel
*  https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel


###      Spring Cloud Alibaba Sentinel 的示例可以参考 sentinel-guide-spring-cloud
* https://github.com/sentinel-group/sentinel-guides/tree/master/sentinel-guide-spring-cloud
1.     第一步，引入POM
   ```xml
   <!-- sentinel  核心组件  -->
   <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
   </dependency>
   ```
2.     第二步，修改配置文件application.yml 
```yaml
spring:    
    cloud:        
        sentinel:
            transport:
                dashboard:  localhost:8080  # Sentinel  dashboard 地址
                port: 8719   #默认8719 端口，假如被占用会自动从8719开始依次+1 扫描，直到找到未被占用的端口
#  暴露端点监控
management:
   endpoints:
      web:
         exposure:
            include:  '*'
```
3.     在sentinel控制台中设置流控规则
4.     访问http://localhost:8800/actuator/sentinel


###    流量控制  
https://sentinelguard.io/zh-cn/docs/flow-control.html
*     流量控制主要有两种统计类型，一种是统计线程数，另外一种则是统计 QPS。类型由 FlowRule.grade 字段来定义。其中，0 代表根据并发数量来限流，1 代表根据 QPS 来进行流量控制。其中线程数、QPS 值，都是由 StatisticSlot 实时统计获取的。
###   线程数限流
*     线程数限流用于保护业务线程数不被耗尽。例如，当应用所依赖的下游应用由于某种原因导致服务不稳定、响应延迟增加，对于调用者来说，意味着吞吐量下降和更多的线程数占用，极端情况下甚至导致线程池耗尽。为应对高线程占用的情况，业内有使用隔离的方案，比如通过不同业务逻辑使用不同线程池来隔离业务自身之间的资源争抢（线程池隔离），或者使用信号量来控制同时请求的个数（信号量隔离）。这种隔离方案虽然能够控制线程数量，但无法控制请求排队时间。当请求过多时排队也是无益的，直接拒绝能够迅速降低系统压力。Sentinel线程数限流不负责创建和管理线程池，而是简单统计当前请求上下文的线程个数，如果超出阈值，新的请求会被立即拒绝

###  关联模式
*     /order  配置关联资源/payment，当/payment 资源达到限流条件时，/order服务也开启限流

###  流控效果
1.     直接拒绝（RuleConstant.CONTROL_BEHAVIOR_DEFAULT）方式。该方式是默认的流量控制方式，当QPS超过任意规则的阈值后，新的请求就会被立即拒绝，拒绝方式为抛出FlowException。这种方式适用于对系统处理能力确切已知的情况下，比如通过压测确定了系统的准确水位时。具体的例子参见 FlowqpsDemo。
2.     冷启动（RuleConstant.CONTROL_BEHAVIOR_WARM_UP）方式。该方式主要用于系统长期处于低水位的情况下，当流量突然增加时，直接把系统拉升到高水位可能瞬间把系统压垮。通过"冷启动"，让通过的流量缓慢增加，在一定时间内逐渐增加到阈值上限，给冷系统一个预热的时间，避免冷系统被压垮的情况。具体的例子参见 WarmUpFlowDemo。
        限流 冷启动  https://github.com/alibaba/Sentinel/wiki/%E9%99%90%E6%B5%81---%E5%86%B7%E5%90%AF%E5%8A%A8
3.     匀速器（RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER）方式。这种方式严格控制了请求通过的间隔时间，也即是让请求以均匀的速度通过，对应的是漏桶算法。具体的例子参见 PaceFlowDemo。

### @SentinelResource 说明 
####   @SentinelResource 用于定义资源，并提供可选的异常处理和 fallback 配置项。 @SentinelResource 注解包含以下属性：

      ● value：资源名称，必需项（不能为空）
      ● entryType：entry 类型，可选项（默认为 EntryType.OUT）
      ● blockHandler / blockHandlerClass: blockHandler 对应处理 BlockException 的函数名称，可选项。blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException。blockHandler 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
      ● fallback：fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。fallback 函数签名和位置要求：
         ○ 返回值类型必须与原函数返回值类型一致；
         ○ 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
         ○ fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
      ● defaultFallback（since 1.6.0）：默认的 fallback 函数名称，可选项，通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）。默认 fallback 函数可以针对所以类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。若同时配置了 fallback 和 defaultFallback，则只有 fallback 会生效。defaultFallback 函数签名要求：
         ○ 返回值类型必须与原函数返回值类型一致；
         ○ 方法参数列表需要为空，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
         ○ defaultFallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
      ● exceptionsToIgnore（since 1.6.0）：用于指定哪些异常被排除掉，不会计入异常统计中，也不会进入 fallback 逻辑中，而是会原样抛出。
*     特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 BlockException 时只会进入 blockHandler 处理逻辑。若未配置 blockHandler、fallback 和 defaultFallback，则被限流降级时会将 BlockException 直接抛出。

###  Sentinel  流控规则的持久化
1.     在Nacos 配置中心增加流控配置文件 cloud-sentinel-service-flow
   ```json
   [
     {
       "resource": "/payment",
       "limitApp": "default",
       "grade": 1,
       "count": 1,
       "clusterMode": false,
       "controlBehavior": 0,
       "strategy": 0,
       "warmUpPeriodSec": 10,
       "maxQueueingTimeMs": 500  
     }
   ]
   ```

2.     在Nacos 配置中心增加熔断配置文件 cloud-sentinel-service-degrade
   ```json
   [
     {
       
       "resource": "/error_count",
       "limitApp": "default",    
       "grade": 2,  
       "count": 2,     
       "statIntervalMs": 1000,
       "timeWindow": 10
     }
   ]
   ```
3.     在Nacos 配置中心，配置热点参数规则
   ```json
   [
     {
       "resource": "hotKey",    
       "grade": 1,
       "paramIdx": 0,
       "count": 2,  
       "durationInSec": 2,
       "controlBehavior": 0, 
       "limitApp": "default", 
       "paramFlowItemList": [
         {
           "classType": "int",    // 参数类型，必须和方法上的参数类型一致，才生效
           "count": 20,
           "object": 3
         }
       ]
     }
   ]
   ```

4.     在Nacos 配置中心增加系统配置文件 cloud-sentinel-service-system
   ```json
   [
     {
       "qps": 1
     }
   ]
   ```