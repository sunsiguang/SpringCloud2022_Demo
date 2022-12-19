# SpringCloud2022_Demo
* 通过 application-profile 实现多服务

## Eureka Server 服务集群
* applicationName: cloud-eureka-server, 
* port: 8761,8762,8763 

## Zookeeper Server 单机  port:2181 
## Consul Server    单机  port:8500 

## Order Service 服务
* applicationName : cloud-order-service
* port: 80 , 使用注册中心：Eureka
* port: 81 , 使用注册中心：Zookeeper
* port: 82 , 使用注册中心：Consul

## Payment Service 服务 
* applictionName : cloud-payment-service
* port : 8001 , 8002 , 使用注册中心：Eureka
* port : 8003 , 8004 , 使用注册中心：Zookeeper
* port : 8005 , 8006 , 使用注册中心：Consul



