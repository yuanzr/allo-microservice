# allo-common

## 简介
公共模块，打包成jar


##目录划分：

1）component：公共组件实现，其它进程通过配置config方式或者@ComponentScan方式实现bean管理
   
2）constants：公共常量类定义

3）exception：业务异常定义,暂时定义了DCException，业务基本异常可以用它抛出

4）kafka：kafka相关，如公共kafka事件 kafkaEvent

5）redis：redis相关，如分布式锁

6）utils：公共工具类相关