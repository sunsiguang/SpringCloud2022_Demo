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

## Nacos  (注册和配置中心)
*     http://localhost:8848/nacos/

##  cloud-payment-nacos  注册服务
1.     引入POM
   ```xml
   <!-- 引入 Nacos Discovery Starter -->
   <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
   </dependency>
   ```
2.     application.yml 
   ```yaml
   spring:
       application:
            name: cloud-payment-service  #服务名
       cloud:
            nacos:
               discovery:
                   server-addr: 127.0.0.1:8848
   ```
3.     启动类
   ```java
   @SpringBootApplication
   @EnableDiscoveryClient
   public class PaymentMain9001 {}
   ```

##  cloud-payment-nacos  配置服务
1.     引入POM
   ```xml
     <!-- 引入 Nacos config Starter 配置中心 -->
     <dependency>
         <groupId>com.alibaba.cloud</groupId>
         <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
     </dependency>

     <!--引入 bootstrap.yml 文件，如果不生效，请引入如下POM　-->
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-bootstrap</artifactId>
     </dependency>
   ```
2.     bootstrap.yml     
*      配置文件优先级（有高到低）  bootstrap.properties >  bootstrap.yml > application.properties > application.yml
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
         active:  dev  #环境标识
  
      # ${prefix}-${spring.profiles.active}.${file-extension}
      # cloud-payment-service-dev.yml
   ```
3.     配置文件的刷新和查询
   ```java
   @RestController
   @RequestMapping("/config")
   @RefreshScope
   public class ConfigController {
   
      @Value("${config.info}")
      private String configInfo;
   }
   ```
4.     验证测试
   ```shell
     http://localhost:83/config/info
     返回： from nacos config center, cloud-payment-service-prod.yaml,version=2, serverPort : 9002
   ```

##  cloud-sentinel-service  sentinel 服务监控，流控，降级 ，熔断配置
*      https://sentinelguard.io/zh-cn/docs/introduction.html
###    Spring Cloud Alibaba Sentinel 的示例可以参考 sentinel-guide-spring-cloud
*       https://github.com/sentinel-group/sentinel-guides/tree/master/sentinel-guide-spring-cloud
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
        [可选] 在代码里添加 @SentinelResource( ...  ), 自定义资源
4.     访问http://localhost:8800/actuator/sentinel


###  Sentinel  流控规则的持久化 Nacos 方式
1.     引入Pom
   ```xml
   <!-- sentinel  nacos  持久化 -->
   <dependency>
       <groupId>com.alibaba.csp</groupId>
       <artifactId>sentinel-datasource-nacos</artifactId>
   </dependency>
   ```
2.     修改配置application.yml 文件
```yaml
spring:
    main:
        # 依赖循环引用是不鼓励的，默认情况下是禁止的
        #  解决升级 spring boot 2.6 后，因循环引用导致启动时报错的问题
        allow-circular-references: true
    application:
        name:   cloud-sentinel-service
    cloud:
        nacos:
            discovery:
                # Nacos 注册中心
                server-addr:  localhost:8848
        sentinel:
            transport:
                dashboard:  localhost:8080  # Sentinel  dashboard 地址
                port: 8719   #默认8719 端口，假如被占用会自动从8719开始依次+1 扫描，直到找到未被占用的端口
            # nacos 持久化数据
            datasource:
                # 可配置多个规则
                # 可自定义key-流控规则
                dashboard-flow:  # 自定义名字
                    nacos:
                        server-addr: localhost:8848
                        dataId:  ${spring.application.name}-flow   # ID--流控规则
                        groupId:  DEFAULT_GROUP
                        data-type:  json
                        rule-type:   flow  # 流控规则
                        #可自定义key-系统规则
                dashboard-system:
                    nacos:
                        server-addr: localhost:8848
                        dataId: ${spring.application.name}-system  # ID--系统规则
                        file-extension: json
                        rule-type: system
                        #namespace: ${spring.profiles.active}
```

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
