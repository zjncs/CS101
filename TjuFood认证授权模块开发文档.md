# **TjuFood认证授权模块开发文档**

本文档旨在为TjuFood项目的后端开发者和前端开发者提供关于用户认证授权模块（包括登录、注册、密码重置）的详细说明。

#### **1. 架构与设计思想**

本模块采用了现代前后端分离项目主流的**Token-Based认证**方案，其核心思想是**无状态（Stateless）**。

- **认证核心**: 用户登录成功后，后端会生成一个全局唯一的`token`作为身份令牌返回给前端。
- **会话管理**: 后端不依赖传统的HTTP Session。用户的登录状态（即会话信息，以`UserDTO`的形式存在）被存储在 **Redis** 中，以`token`作为Key。这种方式我们称之为“基于Redis的共享Session”。
- **状态刷新**: 通过`RefreshTokenInterceptor`拦截器，用户的每一次操作都会自动刷新其在Redis中会话的有效期，实现“无感续期”。
- **安全性**:
  - 用户密码使用业界标准的 **BCrypt** 算法加盐哈希后存储。
  - 前后端通过Token交互，避免了CSRF攻击的风险。
  - API返回的数据均使用`DTO (Data Transfer Object)`，隐藏了数据库实体类的敏感字段。

##### **数据流动图**

- **登录**: `客户端 -> Controller -> Service` (验证凭据) `-> Redis` (存储Session) `-> 返回Token给客户端`
- **已登录请求**: `客户端 (携带Token) -> Interceptor` (验证Token, 刷新TTL, 存User到UserHolder) `-> Controller -> Service` (通过UserHolder获取用户信息)

#### **2. API接口文档**

所有API的基础路径为 `/api`。

##### **2.1 发送验证码**

- **功能**: 为指定的邮箱发送一个用于注册或重置密码的6位数字验证码。

- **URL**: `/api/verifications/code`

- **请求方式**: `POST`

- **请求参数** (Query Parameters):

  - `email` (string, 必需): 目标邮箱地址。
  - `type` (integer, 必需): 验证码类型。`0` 代表注册，`2` 代表忘记密码。

- **成功响应** (`200 OK`):

  ```
  {
      "success": true,
      "errorMsg": null,
      "data": null,
      "total": null
  }
  ```

- **失败响应** (`200 OK` 但 `success: false`):

  ```
  {
      "success": false,
      "errorMsg": "邮箱格式不正确！", // 或 "该邮箱已被注册！"
      "data": null,
      "total": null
  }
  ```

##### **2.2 用户注册**

- **功能**: 使用邮箱和验证码完成新用户注册。

- **URL**: `/api/users/register`

- **请求方式**: `POST`

- **请求体** (Request Body, `application/json`):

  ```
  {
      "email": "test@example.com",
      "password": "yourpassword",
      "confirmPassword": "yourpassword",
      "code": "123456"
  }
  ```

- **成功响应**:

  ```
  {
      "success": true,
      "data": "注册成功！"
  }
  ```

- **失败响应**:

  ```
  {
      "success": false,
      "errorMsg": "验证码错误！" // 或 "两次输入的密码不一致！"
  }
  ```

##### **2.3 用户登录**

- **功能**: 用户通过“账号+密码”的方式登录，账号可以是邮箱或用户ID。

- **URL**: `/api/users/login`

- **请求方式**: `POST`

- **请求体** (Request Body, `application/json`):

  ```
  {
      "account": "test@example.com", // 或 "1001" (用户ID)
      "password": "yourpassword"
  }
  ```

- **成功响应** (返回一个作为身份凭证的`token`):

  ```
  {
      "success": true,
      "data": "a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6" // 这是一个示例token
  }
  ```

- **失败响应**:

  ```
  {
      "success": false,
      "errorMsg": "用户名或密码错误！"
  }
  ```

##### **2.4 重置密码**

- **功能**: 用户在忘记密码后，通过邮箱和验证码重置为新密码。

- **URL**: `/api/users/password`

- **请求方式**: `PUT`

- **请求体** (Request Body, `application/json`):

  ```
  {
      "email": "test@example.com",
      "code": "123456",
      "newPassword": "new-strong-password"
  }
  ```

- **成功响应**:

  ```
  {
      "success": true,
      "data": "密码重置成功！"
  }
  ```

- **失败响应**:

  ```
  {
      "success": false,
      "errorMsg": "验证码错误！"
  }
  ```

#### **3. 前端集成指南**

1. **存储Token**:

   - 在调用登录API (`/api/users/login`) 成功后，前端会从响应的 `data` 字段中获取到 `token` 字符串。
   - 前端需要将这个 `token` 存储在客户端，推荐使用 `localStorage` 或 `sessionStorage`。

2. **发送Token**:

   - 对于所有需要登录才能访问的受保护API（除了登录、注册等少数接口外），前端**必须**在HTTP请求头（Request Headers）中添加 `Authorization` 字段。

   - 该字段的值就是登录时获取到的 `token` 字符串。

   - 示例 (使用 `axios`):

     ```
     import axios from 'axios';
     
     const token = localStorage.getItem('user_token');
     
     const apiClient = axios.create({
       baseURL: '/api',
       headers: {
         'Content-Type': 'application/json',
         'Authorization': token ? `${token}` : ''
       }
     });
     ```

3. **用户登出**:

   - 前端只需要从 `localStorage` 中移除 `token`，然后将页面跳转到登录页即可。后端无需专门的登出接口，因为Redis中的会话数据会在过期后自动被清除。

#### **4. 核心工具在此模块中的作用**

- `PasswordEncoder`: 负责在**注册**和**重置密码**时将明文密码加密，并在**登录**时校验密码的正确性。
- `RegexUtils`: 在**发送验证码**时，校验用户输入的邮箱格式是否合法。
- `RefreshTokenInterceptor`: 模块的“脉搏”。它拦截所有请求，如果请求头中携带了有效的`token`，它会从Redis中取出用户信息存入`UserHolder`，并刷新token有效期。
- `LoginInterceptor`: 模块的“门禁”。它在`RefreshTokenInterceptor`之后执行，检查`UserHolder`中是否有用户信息。如果没有，说明用户未登录，直接返回`401`状态码，拒绝访问。
- `UserHolder`: 提供了一个在单次请求的生命周期内，随时随地获取当前登录用户(`UserDTO`)的便捷方式。