###     Spring Cloud Alibaba Sentinel
*  https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel


###      Spring Cloud Alibaba Sentinel 的示例可以参考 sentinel-guide-spring-cloud
* https://github.com/sentinel-group/sentinel-guides/tree/master/sentinel-guide-spring-cloud


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