###项目分支
* 项目有master,  develop,  release, testing 分支。
* master  主干分支，用来记录版本节点。
* release 发版分支， 所有的发布版本都会从该分支出。
* develop 开发分支，所有开发相关提交都在本分支上进行。当功能完成需要测试时，代码合并到testing分支。
* testing  测试分支， 测试基于的代码分支，当测试提交bug，开发修复bug，首先提交testing分支，之后需要合并到develop分支。
 
###代码review流程
* 代码编码规范使用附件中的ali开发手册规范。
* code review 使用gerrit。代码审核的目的引用网上的一段话说明。
* code review的目的是团队成员在一起共同学习，而不是相互”挑错”.将code review称为代码回顾好一些, 如果大家放弃”挑错”来共同学习,那么代码回顾中学习什么呢? 
* 代码回顾的学习重点是团队成员共同识别模式，这里的模式是指程序员编写代码的习惯,包括”好模式”和”反模式”. 像富有表达力的命名, 单一职责的方法, 良好的格式缩进等,都是”好模式”. 团队成员通过阅读最近编写的测试代码和生产代码来识别”好模式”和”反模式”.既是团队成员之间相互学习的过程, 也是团队整体达成整洁代码共识的过程.
 
###IDE 设置
* 不论使用何种IDE， 文件的换行符要设置为 LF 。而不要使用CRLF。
* 代码内使用 4个空格代替 tab 键。
 
###代码提交 
* 提交代码之前，本地一定要测试ok，至少需要保证编译没错误，服务能启动。

### 代码模块划分 
#### 通用
* 参数校验
    ````
    #请求参数基础性校验 可以通过 ValidatorUtil.validate(T) 校验
    ````
* 注释
  + 类注释
    ````
    /**
     * @description:UserService
     * 用户模块service
     * @author:zhanglida
     * @email:406504302@qq.com
     * @since 4.2
     * @date:2019/3/16
     */
    ````
  + 方法注释
    ````
    /**
     * description
     * @param 
     * @return
     */
    ````

* 类命名
  + 命名方式
   
    驼峰方式命名
    ````
    public class UserInfoModel {
      private Long uid;
      public Long getUid() {
          return uid;
      }
        
      public void setUid(Long uid) {
          this.uid = uid;
      }
    }
    ````
    接口已I开头
    ````
    IUserService
    ````
  + 组成部分

  名词+修饰词+模块 例如：User+Info+Model
  + 不可以为复数
  + 已名词为主
  + 对应表名


* 方法命名
  + 命名方式

    驼峰方式命名
  + 组成部分
  
  + 动词
    ````
    UserInfo select();
    ````
  + 动词+介词+名词 适用于单一业务场景（dao）
    ````
    UserInfo selectByUid(Long uid);
    Boolean deleteByUid(Long uid) 
    Boolean updateStatusByUid(Long uid)
    
    List<UserInfo> getListByUids(List<Long> uids);
    Boolean deleteByUids(List<Long> uids) 
    Boolean updateStatusByUids(List<Long> uids)
    
    ````
  + 业务动词  使用于复杂业务场景（service）
    
    ````
    UserInfo login(LoginRequest request);
    void logout(LogoutRequest request);
    
    ````
  + 返回单条
    ````
    UserInfo getByUid(Long uid);
    ````
  + 返回多条
    返回列表时动词+List+介词+名词复数
    
    ````
    List<UserInfo> getListByUid(Long uid);
    List<UserInfo> getListByUids(List<Long> uids);
    List<UserInfo> getList();
    ````
* 参数
  + 

#### controller
* 单纯处理请求转发与相应
* 遵循restful基本规范
* 请求均已post方式请求
* 方法命名 
  + 读取 get开头
  + 修改 update开头
  + 删除 delete 开头
  + 添加 add 开头
* 取消path变量方式

####manager
* 当业务模型涉及多个service交互时，需要提取manager


####service
* service 暴露接口明确业务意图
* service 单个方法业务逻辑做适当拆分
* 拆分原则，已单个业务功能或代码工能独立方法。
* 对象构建不要放到主题业务代码中，提取独立方法，
* 不可以直接依赖本模块外的Dao
* 适当提取接口
* service以功能模块命名，不是对应表的
  + 正例
    ````
     interface IUserService
    ````
  + 反例
    ````
     interface IUserInfoService
    ````
* service 不可以相互调用


####dao
* 命名
  + updateByUid 修改
  + selectByUid 查询
  + deleteByUid 删除
  + insert      保存
* 返回类型
  + 影响条数的 均返回数值 不要返回布尔值
* dao尽量匹配一张表的操作
* dao缓存逻辑尽量单一
  + 以主键作为唯一标识
* dao默认缓存时间为2天
* dao不可以相互调用
* dao只能返回Model数据
* dao逻辑尽量单一
* jdbc操作使用DB
  + 查询
  ```
  return db.from(ThirdUser.class).where("open_id", openId).first(ThirdUser.class);
  ```
  + 插入
  ```
   return db.insert(userThird);
  ```
  + 修改
  ```
   return db.update(userThird, "sex", "nick", "head", "country", "province", "city");
  ```
  + 删除
  ```
  db.delete(userThird);
  ```

####model
* model除了get set 还可以添加他自身的一些方法。


####缓存
* 基于springboot CacheManager注解
  + @Cacheable 标记读或者存缓存。不需要设置命名空间
  + @CacheExpire 缓存过期，默认缓存2天
  + @KeyParam  指定缓存key，也可指定对象属性
  ```
   @Cacheable
   @CacheExpire
   public ThirdUser query(@KeyParam String openId) {
      return db.from(ThirdUser.class).where("open_id", openId).first(ThirdUser.class);
   }
  ```
  + @CacheEvict 清楚缓存
   ```
   @CacheEvict
   public int update(@KeyParam(key = "openId") ThirdUser userThird) {
      return db.update(userThird, "sex", "nick", "head", "country", "province", "city");
   }
  ```
* 复杂对象缓存
  + BaseCacheDao 封装了复杂对象缓存
  ```
  Map<String, ThirdUser> map = CollectionUtils.toMap(userThirds, ThirdUser::getOpenId);
  super.cacheObjects(map);
  ```
  + RedisTemplateWrapper 用于处理业务层面的复杂逻辑缓存
  

###协议

* 通用化
  + 结构
  ```
  public class ProtocolResponse<T> {
      private int code;
      private String msg;
      private T result;
  }
  ```
* 模块化
  + 正例
  ```
  class UserRequest{
    static class LoginRequest{
    }
  }
  ```
  +反例
  ```
  class UserLoginRequest{}
  ````
  

###异常
* 业务异常
  ```
  public class BusinessException extends RuntimeException {
      private int code;
      private String msg;
      private Object result;
  }
  
  ```
* 异常枚举
  ```
  public interface IBusinessError {
  
      int getCode();
  
      String getMessage();
  }
  
  public enum CommonBusinessError implements IBusinessError{
      /**
       * 0 正常返回
       */
      NO_ERROR(0, "成功"),
      /**
       * 错误码定义类
       * 1000 - 9999 为系统错误码
       */
      SYSTEM_UNKNOWN_ERROR(1000, "系统未知错误."),
      REQUEST_PARAMS_INVALID(1001, "请求参数非法."),
      REQUEST_DECRYPT_FAIL(1002, "请求解密错误."),
      REQUEST_URL_NOT_FUND(10003, "请求地址不存在"),
      ;
  
      CommonBusinessError(int code, String message) {
          this.code = code;
          this.message = message;
      }
  
      private int code;
      private String message;
  
      public int getCode() {
          return code;
      }
  
      public String getMessage() {
          return message;
      }
  
  ```
* 不可以printStackTrace
* 尽量封装业务异常
  

###日志
* 统计使用LoggerUtils 获取logger对象
* 日志普通的debug,info 错误的打error。
* logger方法参数直接用当前类名
    ````
    private static final Logger logger = LoggerUtils.getLogger(WxApiUtil.class);
    ````
* 异常信息不可以直接print到控制台
  + 错误示例：
    ```
    catch (IOException e) {
      e.printStackTrace();
    }
    ```
  + 正确示例：
    ```
    catch (IOException e) {
      logger.error("xxx fail",e);
    }
    ```
###包导入
```
mvn install:install-file -Dfile=cube-base-pay/lib/sdk-java-20170307171631.jar -DgroupId=com.alipay -DartifactId=sdk-java -Dversion=20170307171631 -Dpackaging=jar

```
