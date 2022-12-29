####  验证查询服务
* http://127.0.0.1:8848/nacos/v1/ns/catalog/instances?serviceName=cloud-payment-service&clusterName=DEFAULT&pageSize=10&pageNo=1&namespaceId=

####  nacos config demo 
*  https://github.com/nacos-group/nacos-examples/tree/master/nacos-spring-cloud-example/nacos-spring-cloud-config-example

####   nacos上的配置文件： cloud-payment-service-dev.yaml 
```yaml
server:
    port: 9001
spring:
    application:
        name: cloud-payment-service  #服务名
    cloud:
        nacos:
            discovery:
                server-addr: 127.0.0.1:8848
management:
    endpoints:
        web:
            exposure:
                 include: '*'
config:
    info: from nacos config center,cloud-payment-service-dev.yaml,version=2
```

####   nacos 上的配置文件： cloud-payment-service-prod.yaml  
```yaml
server:
    port: 9002
spring:
    application:
         name: cloud-payment-service  #服务名
    cloud:
        nacos:
            discovery:
                server-addr: 127.0.0.1:8848
management:
    endpoints:
        web:
            exposure:
                 include: '*'
config:
    info: from nacos config center, cloud-payment-service-prod.yaml,version=2
```

####  bootstrap.yml  本机 配置文件
```yaml
spring:
  application:
    name: cloud-payment-service  #服务名
  cloud:
    nacos:
      discovery:
          server-addr: 127.0.0.1:8848  #注册中心
      config:
          server-addr: 127.0.0.1:8848  #配置中心
          file-extension:  yaml  #指定yaml 配置文件格式

  profiles:
      active:  dev  #默认环境标识
```