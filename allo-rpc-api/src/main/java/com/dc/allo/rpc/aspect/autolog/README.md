 ## 自动日志组件    
 ### 工具说明 自动日志组件使用spring-aop切入allo项目中的controller，service，kafka，mq，mapper等常用业务逻辑代码所存放的包中，接管所有通过spring-aop代理调用的方法（self-invocation 即在类的方法中调用该类自己的其他方法，是不被spring-aop所代理的，因此也没办法被自动日志组件切面，具体原理见spring-aop官网：[Spring-aop-understanding-aop-proxies](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#aop-understanding-aop-proxies) ）。    
    
被接管的方法在执行前，会判定是否需要自动打印日志，如果需要，则会在方法执行前打印入参，在方法执行返回后或者抛出异常后打印返会结果或者异常信息，日志内容格式如下：    
```text 
//执行正常返回的情况，打印日志： 
[AutoLog]-[in] class: com.dc.allo.roomplay.service.pk.SampleServiceImpl method: getUser argTypes: EMPTY_TYPES argVals: EMPTY_VALS context: c84d5278-0449-4afa-9da5-f754d64bef33  
[AutoLog]-[out] class: com.dc.allo.roomplay.service.pk.SampleServiceImpl method: pageAdmins cost: 7.569ms returnValType: AlloResp returnVal: AlloResp{code=0, message='处理成功', data=CheckResp{appName='m-voice-activity', time=Wed Jan 08 13:27:02 CST 2020, ip='null', hostname='null'}} context: c84d5278-0449-4afa-9da5-f754d64bef33  
//执行抛出异常的情况，打印日志： 
[AutoLog]-[before execution] class: com.dc.allo.roomplay.service.pk.SampleServiceImpl method: getUser argTypes: EMPTY_TYPES argVals: EMPTY_VALS context: a6d1cf22-457f-4b07-826d-5bae8456d45f [AutoLog]-[after execution and throw exception] class: com.yy.mobilevoice.sy.activity.controller.CheckController method: helloReq context: a6d1cf22-457f-4b07-826d-5bae8456d45f msg: 这是一个异常     
 at com.dc.allo.common.aspect.autolog.AutoLogAspect.executeWithLog(AutoLogAspect.java:212)    
 at com.dc.allo.common.aspect.autolog.AutoLogAspect.autoLog(AutoLogAspect.java:104) at com.dc.allo.common.aspect.autolog.AutoLogAspect.executeWithLog(AutoLogAspect.java:212) at com.yy.mobilevoice.common.aspect.autolog.AutoLogAspect.autoLog(AutoLogAspect.java:104) ...  
 ``` 
 ---   
### 使用步骤：  
- 1.在要使用的spring-boot项目中，确保controller,service,mapper,kafka,mq目录的层级结构属于com.dc.allo下的直接子目录或嵌套子目录（目前allo的项目都符合）    
- 3.controller,service,mapper,kafka,mq以外的目录使用自动日志组件，需要使用@AutoLog注解单独配置（注解必须配置在spring-bean中的方法上才会生效）    
- 4.在表nacos的配置中心里配置需要打印日志的方法，配置格式如下（配置后1min内生效）    
```json 
{ "全量类名":[    
         "方法名1",    
         "方法名2"    
], "com.dc.allo.roomplay.service.pk.impl.SampleServiceImpl":[    
         "getUser"    
], "com.dc.allo.roomplay.service.pk.impl.SampleServiceImpl":[    
         "getUser",    
         "pageAdmins"   
], "com.dc.allo.roomplay.service.pk.impl.SampleServiceImpl":[    
         "*"  //如果方法名数组中配置了一个*，则该类下所有方法都生效，无需再配置指定方法  
 ] } 
 ```
- 5.如果在controller,service,mapper,kafka,mq目录下存在使用了@Scheduled的方法，请确保使用这些方法的访问修饰符级别高于private，否则将出现如下报错： 

``` text  
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'checkController' defined in file [/Users/zhangzhenjun/workspace/git/allo-roomplay-service/target/classes/com/dc/allo/roomplay/service/pk/SampleServiceImpl.class]: Initialization of bean failed; nested exception is java.lang.IllegalStateException: Need to invoke method 'test' found on proxy for target class 'SampleServiceImpl' but cannot be delegated to target bean. Switch its visibility to package or protected.  
 at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:589) at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:503)..  
Caused by: java.lang.IllegalStateException: Need to invoke method 'task' found on proxy for target class 'SampleServiceImpl' but cannot be delegated to target bean. Switch its visibility to package or protected.  
..
```  
--- 
### 代码目录 自动日志组件代码在allo-common项目的aspect目录下：    
- 1.SystemCommonPointCut.java --allo系统级切点集（以后有aop需求，建议也写在这里面）    
- 2.AutoLog.java --自动日志注解（用于除controller、service等外的其他包内的也需要自动打印日志的方法使用）    
- 3.AllowAutoLogMethods.java --允许打印日志的方法集合对象抽象    
- 4.AutoLogAspect.java --自动日志切面主逻辑  
  
### 使self-invocation也能自动打印日志  
- 1.在springboot启动类上加上注解 **@EnableAspectJAutoProxy(exposeProxy = true)** 
- 2.使用 **AopContext.currentProxy()** 的方式实现self-invocation，如下：  
```java  
@RestController public class CheckController {    
  private static final Logger logger = LoggerFactory.getLogger(CheckController.class);    
  @RequestMapping("/back/check")    
  @AutoLog    
  public AlloResp<Sample> hello() {    
      AlloResp resp = new AlloResp();    
     logger.warn(checkResp.toString());    
     ((CheckController)AopContext.currentProxy()).test("hello1");    
     test("hello2");    
    return AlloResp.success(resp);    
  }  
 public String test(String s){    return s+"_local";    
 }  
}  
```  
- 3.将test方法配置到自动日志组件的方法白名单，请求时将打印如下日志：  
```text  
[AutoLog]-[in] class: com.dc.allo.sy.roomplay.controller method: hello argTypes: EMPTY_TYPES argVals: EMPTY_VALS context: 6ad84bca-018d-48da-b80e-82c202a9b6a1  
[AutoLog]-[in] class: com.dc.allo.sy.roomplay.controller method: test argTypes: [String] argVals: [hello1] context: 3c35b32b-a905-4d72-a3b5-c72a8f292519  
[AutoLog]-[out] class: com.dc.allo.sy.roomplay.controller method: test cost: 5.231ms returnValType: String returnVal: hello1_local context: 3c35b32b-a905-4d72-a3b5-c72a8f292519  
[AutoLog]-[out] class: com.dc.allo.sy.roomplay.controller method: hello cost: 7.256ms returnValType: AlloResp returnVal: AlloResp{code=0, message='处理成功', data=CheckResp{appName='allo-roomplay-service', time=Wed Mar 20 12:51:37 CST 2020, ip='null', hostname='null'}} context: 6ad84bca-018d-48da-b80e-82c202a9b6a1  
```  
观察日志参数可以发现，使用 **AopContext.currentProxy()** 方式调用test()方法可以打出日志，而自身调用test()方法则不可以。