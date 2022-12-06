# SpringCloud2022_Demo,通过 application-profile 实现多服务

Eureka Server 服务集群, applicationName: cloud-eureka-server, port: 8761,8762,8763 

Order Service  ,  applicationName : cloud-order-service
port :80,  使用注册中心是Eureka

Payment Service , applictionName : cloud-payment-service
port : 8001 , 8002 , 使用的注册中心是Eureka
port : 8004,  使用的注册中心是zookeeper

