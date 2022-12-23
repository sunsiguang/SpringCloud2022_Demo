## 远端配置文件读取，测试步骤
### (刷新config-server, 自动更新config-client，使用BUS + RabbitMQ)
1.  修改 https://gitee.com/sunsgkv/cloud-config-server/blob/master/config-dev.yml  配置文件
2.  查看configServer 配置文件(从gitee上拉取)：http://config8888.com:8888/config-dev.yml
3.  手动刷新服务端， 刷新配置文件，通过bus+rabbitMQ 自动刷新client  ：    
     http://localhost:8888/actuator/busrefresh  (POST)

[//]: # (     {"_links":{"self":{"href":"http://localhost:8888/actuator","templated":false},"busrefresh":{"href":"http://localhost:8888/actuator/busrefresh","templated":false},"busrefresh-destinations":{"href":"http://localhost:8888/actuator/busrefresh/{*destinations}","templated":true}}})
4.  客户端 查看 配置文件(从configServer 上拉取)    http://localhost:3355/configinfo
5.  客户端 查看 配置文件(从configServer 上拉取)    http://localhost:3366/configinfo
