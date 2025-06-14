# **项目通用工具类 (Utils) 使用说明 **

本文档详细介绍了 `xyz.tjucomments.tjufood.utils` 包及其子包下核心工具类的功能和使用方法。这些工具旨在解决开发中的常见问题，提升开发效率和系统性能。

### **一、 使用前置条件**

在开始使用这些工具前，请确保您的项目已满足以下条件：

1. **Maven 依赖已添加**: 确保 `pom.xml` 文件中已包含 `hutool-all` 依赖。

   ```
   <dependency>
       <groupId>cn.hutool</groupId>
       <artifactId>hutool-all</artifactId>
       <version>5.8.25</version>
   </dependency>
   ```

2. **线程池配置已创建**: 项目中存在 `ThreadPoolConfig.java`，用于为缓存重建提供线程池支持。

3. **Lua 脚本已放置**: 在 `src/main/resources` 目录下已创建 `lua/unlock.lua` 脚本，用于分布式锁的原子性释放。

4. **配置文件已更新**: `application.yml` 中已配置好数据库、Redis以及项目自定义的路径等信息。

### **二、 核心工具详解**

#### **1. 缓存工具 (`utils.cache.\*`)**

- **核心类**: `CacheClient`
- **辅助类**: `RedisData`
- **目的**: 解决与Redis缓存交互时常见的**缓存穿透**、**缓存击穿**问题，简化缓存逻辑。

##### **如何使用 `CacheClient`**

在需要使用缓存的Service中注入 `CacheClient`。

**场景A：解决“缓存穿透”** 使用 `queryWithPassThrough` 方法。它会缓存空对象来防止无效请求持续访问数据库。

```
public Shop queryShopById(Long id) {
    return cacheClient.queryWithPassThrough(
            RedisConstants.CACHE_SHOP_KEY, // 缓存Key的前缀
            id,                            // 查询的ID
            Shop.class,                    // 返回值的类型
            shopId -> shopMapper.selectById(shopId), // 缓存未命中时，查询数据库的逻辑
            RedisConstants.CACHE_SHOP_TTL, // 缓存有效期
            TimeUnit.MINUTES               // 时间单位
    );
}
```

**场景B：解决“缓存击穿”** 使用 `queryWithLogicalExpire` 方法。它通过逻辑过期时间，在热点数据过期时返回旧数据，并异步重建缓存。

```
public Shop queryShopByIdWithLogicalExpire(Long id) {
    return cacheClient.queryWithLogicalExpire(
            RedisConstants.CACHE_SHOP_KEY,
            id,
            Shop.class,
            shopId -> shopMapper.selectById(shopId),
            RedisConstants.CACHE_SHOP_TTL,
            TimeUnit.SECONDS
    );
}
```

#### **2. 分布式ID生成器 (`utils.id.\*`)**

- **核心类**: `RedisIdWorker`
- **目的**: 在分布式环境下生成全局唯一的、趋势递增的64位长整型ID。

##### **如何使用 `RedisIdWorker`**

在需要生成ID的Service中注入 `RedisIdWorker`，并调用 `nextId("业务前缀")` 方法。

```
long orderId = redisIdWorker.nextId("order");
```

#### **3. 分布式锁 (`utils.lock.\*`)**

- **核心类**: `SimpleRedisLock`
- **接口**: `ILock`
- **目的**: 在高并发场景下，确保对共享资源的访问是互斥的。

##### **如何使用 `SimpleRedisLock`**

在需要加锁的方法中，实例化 `SimpleRedisLock`。**必须使用 `try-finally` 结构确保锁一定会被释放**。

```
ILock lock = new SimpleRedisLock("order:" + userId, stringRedisTemplate);
if (!lock.tryLock(10)) { // 尝试获取锁
    return Result.fail("操作频繁，请稍后再试");
}
try {
    // ... 执行核心业务逻辑
} finally {
    lock.unlock(); // 释放锁
}
```

#### **4. 正则表达式校验工具 (`utils.validation.\*`)**

- **核心类**: `RegexUtils`, `RegexPatterns`
- **目的**: 对用户输入的数据（如手机号、邮箱等）进行格式校验。

##### **如何使用 `RegexUtils`**

这是一个纯静态方法的工具类，无需注入，直接通过类名调用。

```
if (RegexUtils.isPhoneInvalid(phone)) {
    return Result.fail("手机号格式错误！");
}
```

#### **5. 用户信息持有器 (`utils.auth.\*`)**

- **核心类**: `UserHolder`
- **目的**: 在**同一个请求的处理线程**中，方便地存储和获取当前登录的用户信息（通常是 `UserDTO`），避免在方法间繁琐地传递用户参数。

##### **如何使用 `UserHolder`**

1. **存入用户**: 在用户身份被确认后（例如，在你的 `AuthorInterceptor` 或Spring Security的认证成功处理器中），将用户信息存入 `UserHolder`。

   ```
   // 假设你已经从数据库查到了user, 并转换成了userDTO
   UserDTO userDTO = new UserDTO();
   // ... 从 user 复制属性到 userDTO
   UserHolder.saveUser(userDTO); // 存入当前线程
   ```

2. **获取用户**: 在后续的Service或Controller的任何地方，随时可以获取。

   ```
   UserDTO user = UserHolder.getUser();
   Long userId = user.getId();
   ```

3. **移除用户**: 为防止内存泄漏，**必须**在请求处理完毕后将用户移除。这通常在拦截器的 `afterCompletion` 方法中完成。

   ```
   @Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       UserHolder.removeUser();
   }
   ```

#### **6. 常量类 (`utils.constants.\*`)**

- **核心类**: `RedisConstants`, `SystemConstants`
- **目的**: 集中管理项目中的所有魔法值（如Redis Key前缀、固定数字等），提高代码的可读性和可维护性。

##### **如何使用常量**

在代码中直接通过 `类名.常量名` 的方式引用，避免硬编码。

```
// 使用Redis常量
String key = RedisConstants.CACHE_SHOP_KEY + id;
```