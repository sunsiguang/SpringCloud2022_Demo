# SpringCloud2022_Demo
* 通过 application-profile 实现多服务

## Eureka Server 服务集群
### applicationName: cloud-eureka-server, 
* port: 8761,8762,8763 
### hosts文件配置(C:\Windows\System32\drivers\etc)
* 127.0.0.1 eureka8761.com
* 127.0.0.1 eureka8762.com
* 127.0.0.1 eureka8763.com

## Zookeeper Server 单机  port:2181 
*      官网  https://zookeeper.apache.org/doc/current/zookeeperStarted.html
1.     创建配置文件:  create it in conf/zoo.cfg
2.     启动Server：  bin/zkServer.sh start
3.     连接测试：      bin/zkCli.sh -server 127.0.0.1:2181
4.     查看节点：      ls /     或   get /zk_tes
5.     引入POM
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
    </dependency>
    ```
6.     application.yml配置
    ```yml
     #注册zookeeper中心地址
      cloud:
        zookeeper:
          connect-string:  localhost:2181
    ```
7.     启动类增加注册中心注解
    ````java
        @EnableDiscoveryClient  //客户发现注册服务zookeeper和consul 使用
        public class PaymentMain8004 {}
    ````

## Consul Server    单机  port:8500   
*      官网：https://www.consul.io/
1.      查看版本： consul  --version 
2.      启动：  consul agent -dev  (开发模式运行)
3.      通过 http://localhost:8500 访问页面
4.      引入POM
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
    ```
5.     application.yml配置
    ````yaml
      #注册Consul中心地址
      cloud:
        consul:
          host: localhost
          port: 8500   #默认端口
          discovery:
            #hostname:127.0.0.1
            service-name: ${spring.application.name}
    ````
6.     启动类增加注册中心注解
    ````java
        @EnableDiscoveryClient  //客户发现注册服务zookeeper和consul 使用
        public class PaymentMain8004 {}
    ````


## Order Service 服务(消费者)
* applicationName : cloud-order-service
* port: 80 , 使用注册中心：Eureka
* port: 81 , 使用注册中心：Zookeeper
* port: 82 , 使用注册中心：Consul

## Payment Service 服务(生产者)
* applictionName : cloud-payment-service
* port : 8001 , 8002 , 使用注册中心：Eureka
* port : 8003 , 8004 , 使用注册中心：Zookeeper
* port : 8005 , 8006 , 使用注册中心：Consul

## SpringCloud Gateway Service 网关(9527)
1.     引入pom依赖
```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
```
2.      application.yaml 增加路由配置
    ```yml
    spring:
      application:
         name: cloud-gateway-service
      cloud:
        gateway:
          discovery:
              locator:
                 enabled: true  #开启从注册中心动态创建路由的功能，利用微服务名进行路由
          routes:
            - id: payment-dyna-routh
              uri: lb://cloud-payment-service  #动态路由
              predicates:
                - Path=/pay/lb/**   #断言，路径相匹配的进行路由
    ```

## SpringCloud Config Server 配置中心 ，后续 Nacos 可以替代
*     官网： https://docs.spring.io/spring-cloud-config/docs/current/reference/html/
*     127.0.0.1 config8888.com
1.     引入POM
    ```xml
    <!-- 服务端 配置中心 Config 支持-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    ```
2.     application.yml  增加配置
    ```yaml
    spring:  
      cloud:
        config:
          server:
            git:
    #          uri: https://github.com/sunsiguang/cloud-config-server.git  # github 上的仓库名字
              uri:  https://gitee.com/sunsgkv/cloud-config-server.git             # gitee 上仓库暂时需要 public
              search-paths:    #搜索目录
                - cloud-config-server
              label: master
              default-label: master   # 添加不会报错，但不影响使用
    management:  # 关键步骤 ： rabbitMQ 相关配置，暴露bus 刷新配置的端点，利用rabbitMQ 和  actuator  ( Config Server 端配置)
        endpoints:  # 暴露bus 刷新配置的端点
            web:
                exposure:
                     include:  bus-refresh
    ```
3.     **启动类增加注解：**
    ```java
    @EnableEurekaClient
    @EnableConfigServer  // Config 配置类
    public class ConfigServerMain {}
    ```

##  SpringCloud Config Client 客户端配置 
1.    引入POM
        ```xml
        <!-- 客户端 配置中心数据获取  Config 支持-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        ```
   2.     bootstrap.yml 配置
       ```yaml
       spring:   
           cloud:
              config:
                   label: master  # 分支名称
                   name: config  #  配置文件名称
                   profile: dev    #  读取后缀名称  ， 上述： master分支上的 config-dev.yml 配置文件
                   uri:  http://localhost:8888  #配置中心地址
       # 暴漏监控端点, 配置文件自动刷新
       management:
         endpoints:
           web:
             exposure:
               include:  "*"
       ```

## SpringCloud Bus  消息总线 
*     官网： https://docs.spring.io/spring-cloud-bus/docs/current/reference/html/
*     ( 通过 RabbiitMQ 和 actuator 实现 ) , Nacos 可以替代
1.     引入POM
    ```xml
    <!-- 添加消息总线 BUS RabbitMQ 支持-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bus-amqp</artifactId>
    </dependency>
    ```
2.     application.yml 配置
    ```yaml
      # 添加 rabbitMQ 配置支持，用于BUS 消息总线推送，注意：Spring.rabbitmq.***
      rabbitmq:
          host: localhost
          port: 5672
          username: guest
          password: guest
    ```

## RabbitMQ Server  (https://www.rabbitmq.com/)
*  http://127.0.0.1:15672/
1.      安装Erlang  , 查看是否安装成功： erl -v
2.     安装RabbitMQ
    ```shell
    # 解压
    rpm -Uvh rabbitmq-server-3.8.14-1.el7.noarch.rpm
    # 安装
    yum install -y rabbitmq-server
    # 启动rabbitmq
    systemctl start rabbitmq-server
    # 查看rabbitmq状态
    systemctl status rabbitmq-server
   
   # 设置rabbitmq服务开机自启动
    systemctl enable rabbitmq-server
    # 关闭rabbitmq服务
    systemctl stop rabbitmq-server
    # 重启rabbitmq服务
    systemctl restart rabbitmq-server
    ```
3.     安装启动RabbitMQWeb管理界面
    ```shell
    rabbitmq-plugins enable rabbitmq_management
    # 访问：http://127.0.0.1:15672/
    ```

## ZipKin  + Sleuth   (链路监控)
    Zipkin官网： https://zipkin.io/ 
    Sleuth官网 :  https://spring.io/projects/spring-cloud-sleuth
    下载地址：    https://repo1.maven.org/maven2/io/zipkin/zipkin-server/
    运行：java -jar zipkin-server-2.24.0-exec.jar
*  http://localhost:9411/zipkin/
1.     引入POM
    ```xml
    <!-- 包含了sleuth + zipkin  链路追踪组件-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zipkin</artifactId>
        <version>2.2.8.RELEASE</version>
    </dependency>
    ```
2.     application.yml 增加配置
    ```yaml
    spring:
      zipkin: # 增加 链路追踪 配置类  , # zipkin的服务地址
        base-url: http://localhost:9411
      sleuth:
        sampler:
          # 采样率，介于0到1之间，1表示全部采集
          probability: 1
    ```


