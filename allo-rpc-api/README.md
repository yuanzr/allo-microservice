# allo-rpc-api

## 简介
rpc-api进程：服务间调用api定义，打包成jar


##目录划分：

1）api：rpc-api定义， 暂时不针对进程做api接口拆分，后续业务扩展了可考虑，进程间接口依赖粒度更细
   
2）domain：rpc-domain，对外序列化对象定义 ，必须继承Serializable接口，否则dubbo调用会报错

3）proto：rpc-proto，对外协议定义。AlloResp类为返回基类，dubbo接口是否都要做为基类返回待定


