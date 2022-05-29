# allo-biz-notice

## 简介
消息通知模块，包括融云的消息下发接口，app各种公共通知接口，如动画下发等


##目录划分：

1）component：公共组件实现，其它进程通过配置config方式或者@ComponentScan方式实现bean管理
   
2）constants：公共常量类定义

3）exception：业务异常定义,暂时定义了DCException，业务基本异常可以用它抛出

4）kafka：kafka相关，如公共kafka事件 kafkaEvent

5）redis：redis相关，如分布式锁

6）utils：公共工具类相关

7）controller： http controller ，一般对外部暴露接口放这，路径规则为：/模块名/
    /pb/         pb相关接口加此路径
    /pb/auth/    需要鉴权接口加此路径
    /admin/      后台管理接口加此路径，需要加注解@ipcheck ，限定特定服务器才能访问
    全路径例子：   gateway.allolike.com/allo-roomplay-service/pk/pb/auth/xxxx
                 gateway.allolike.com/allo-roomplay.service/pk/admin/xxxx