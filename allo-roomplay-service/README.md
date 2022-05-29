# allo-roomplay-service

## 简介
房间玩法服务进程：包括pk玩法


##目录划分：

1）config：config方式注入bean，一般包括redis，mysql等
   
2）mapper：数据库dao层，命名规则统一，插入用add前缀，更新用update前缀，删除用del前缀，获取单个对象用get前缀，查询列表用query前缀，尽量减少对大表的连表查询

3）service：业务服务层，内部service接口，缓存使用非必要（如配置信息）除外，都得设置过期时间，不允许当数据库使用，统一收纳到cache接口调用，避免使用地方过于分散，通过RedisUtil类处理

4）service.rpc：对外服务层，dubbo接口，不同进程间通讯用dubbo，注意，dubbo接口命名统一加Dc前缀，和普通service接口做区分

5）handler：一般异步消息处理类

6）listener：监听类

7）kafka： kafka相关

8）controller： http controller ，一般对外部暴露接口放这，路径规则为：/模块名/
    /pb/         pb相关接口加此路径
    /pb/auth/    需要鉴权接口加此路径
    /admin/      后台管理接口加此路径，需要加注解@ipcheck ，限定特定服务器才能访问
    全路径例子：   gateway.allolike.com/allo-roomplay-service/pk/pb/auth/xxxx
                 gateway.allolike.com/allo-roomplay.service/pk/admin/xxxx

