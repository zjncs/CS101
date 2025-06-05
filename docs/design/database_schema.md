# 天津大学美食社区平台数据库架构

## 实体关系图概述

本数据库架构基于ER模型设计，主要包含以下核心实体与关系：

1. **用户实体**：包括普通用户、管理员和系统管理者
2. **校区与食堂实体**：学校的不同校区及其食堂
3. **窗口与菜品实体**：食堂内的窗口及其提供的菜品
4. **博客与内容实体**：用户发布的美食分享内容
5. **评价与评论实体**：用户对食堂、窗口、菜品的评价及评论
6. **社交互动实体**：包括点赞、收藏、关注等社交功能
7. **激励系统实体**：包括积分、勋章、优惠券等激励机制
8. **系统管理实体**：包括系统配置、日志、公告等系统功能

本文件收录了各表结构及示例插入语句，方便在设计和实现过程中查阅。内容取材自前置的需求说明。

## 数据库表设计

### 1. 用户与权限模块 (User & Permissions)

管理用户账号、认证、角色及权限。

#### 1.1 `tb_user` - 用户表

**描述**：存储系统所有用户的基本信息。

| 字段名        | 类型           | 说明                                      | 约束说明           |
| ------------- | -------------- | ----------------------------------------- | ------------------ |
| `id`          | `BIGINT`       | 用户ID                                    | 主键, 自增         |
| `username`    | `VARCHAR(50)`  | 登录用户名                                | 唯一, 非空         |
| `password`    | `VARCHAR(100)` | 密码（DES加密存储）                       | 非空               |
| `phone`       | `VARCHAR(20)`  | 手机号                                    | 唯一, 可空         |
| `email`       | `VARCHAR(100)` | 邮箱                                      | 唯一, 可空         |
| `nickname`    | `VARCHAR(50)`  | 昵称                                      | 非空               |
| `icon`        | `VARCHAR(255)` | 头像URL                                   | 可空, 默认系统头像 |
| `gender`      | `TINYINT`      | 性别 (0=女, 1=男, 2=未知)                 | 非空, 默认2        |
| `birthday`    | `DATE`         | 生日                                      | 可空               |
| `bio`         | `VARCHAR(300)` | 个人简介                                  | 可空               |
| `campus`      | `VARCHAR(50)`  | 校区                                      | 可空               |
| `credits`     | `INT`          | 积分                                      | 非空, 默认0        |
| `level`       | `INT`          | 用户等级                                  | 非空, 默认1        |
| `status`      | `TINYINT`      | 状态 (0=正常, 1=禁用)                     | 非空, 默认0        |
| `role`        | `TINYINT`      | 角色 (0=普通用户, 1=管理员, 2=系统管理者) | 非空, 默认0        |
| `create_time` | `DATETIME`     | 创建时间                                  | 非空, 默认当前时间 |
| `update_time` | `DATETIME`     | 更新时间                                  | 非空, 默认当前时间 |

**插入样例**：

```sql
INSERT INTO tb_user (username, password, phone, email, nickname, gender, campus, credits, level, role, create_time)
VALUES ('zhangsan', 'DES_ENCRYPT_PASSWORD', '13800138000', 'zhangsan@tju.edu.cn', '张三', 1, '北洋园校区', 100, 2, 0, NOW());
```

#### 1.2 `tb_verification` - 验证码表

**描述**：存储短信或邮箱验证码。

| 字段名        | 类型           | 说明                                  | 约束说明           |
| ------------- | -------------- | ------------------------------------- | ------------------ |
| `id`          | `BIGINT`       | 验证码ID                              | 主键, 自增         |
| `phone`       | `VARCHAR(20)`  | 手机号                                | 可空               |
| `email`       | `VARCHAR(100)` | 邮箱                                  | 可空               |
| `code`        | `VARCHAR(10)`  | 验证码                                | 非空               |
| `type`        | `TINYINT`      | 验证类型 (0=注册, 1=登录, 2=找回密码) | 非空               |
| `expire_time` | `DATETIME`     | 过期时间                              | 非空               |
| `status`      | `TINYINT`      | 状态 (0=未使用, 1=已使用, 2=已过期)   | 非空, 默认0        |
| `create_time` | `DATETIME`     | 创建时间                              | 非空, 默认当前时间 |

**插入样例**：

```sql
INSERT INTO tb_verification (phone, code, type, expire_time, create_time)
VALUES ('13800138000', '123456', 0, DATE_ADD(NOW(), INTERVAL 5 MINUTE), NOW());
```

#### 1.3 `tb_admin` - 管理员表

**描述**：存储管理员的额外信息。

| 字段名         | 类型          | 说明                  | 约束说明                         |
| -------------- | ------------- | --------------------- | -------------------------------- |
| `id`           | `BIGINT`      | 管理员ID              | 主键, 自增                       |
| `user_id`      | `BIGINT`      | 对应用户ID            | 外键 -> `tb_user.id`, 唯一, 非空 |
| `name`         | `VARCHAR(50)` | 姓名                  | 非空                             |
| `status`       | `TINYINT`     | 状态 (0=正常, 1=禁用) | 非空, 默认0                      |
| `appointed_by` | `BIGINT`      | 任命管理员ID          | 外键 -> `tb_admin.id`, 可空      |
| `create_time`  | `DATETIME`    | 创建时间              | 非空, 默认当前时间               |
| `update_time`  | `DATETIME`    | 更新时间              | 非空, 默认当前时间               |

**插入样例**：

```sql
INSERT INTO tb_admin (user_id, name, appointed_by, create_time)
VALUES (2, '李四', 1, NOW());
```

#### 1.4 `tb_role` - 角色表

**描述**：定义系统中的角色。

| 字段名        | 类型           | 说明                  | 约束说明           |
| ------------- | -------------- | --------------------- | ------------------ |
| `id`          | `BIGINT`       | 角色ID                | 主键, 自增         |
| `name`        | `VARCHAR(50)`  | 角色名称              | 唯一, 非空         |
| `description` | `VARCHAR(255)` | 角色描述              | 可空               |
| `status`      | `TINYINT`      | 状态 (0=正常, 1=禁用) | 非空, 默认0        |
| `create_time` | `DATETIME`     | 创建时间              | 非空, 默认当前时间 |
| `update_time` | `DATETIME`     | 更新时间              | 非空, 默认当前时间 |

**插入样例**：

```sql
INSERT INTO tb_role (name, description, create_time)
VALUES ('内容审核员', '负责审核用户发布的评价和评论', NOW());
```

#### 1.5 `tb_admin_role` - 管理员角色表

**描述**：管理员与角色的多对多关联。

| 字段名                           | 类型       | 说明       | 约束说明              |
| -------------------------------- | ---------- | ---------- | --------------------- |
| `id`                             | `BIGINT`   | ID         | 主键, 自增            |
| `admin_id`                       | `BIGINT`   | 管理员ID   | 外键 -> `tb_admin.id` |
| `role_id`                        | `BIGINT`   | 角色ID     | 外键 -> `tb_role.id`  |
| `create_time`                    | `DATETIME` | 创建时间   | 非空, 默认当前时间    |
| `UNIQUE KEY (admin_id, role_id)` |            | 复合唯一键 | 防止重复分配          |

**插入样例**：

```
INSERT INTO tb_admin_role (admin_id, role_id, create_time)
VALUES (2, 1, NOW());
```

#### 1.6 `tb_permission` - 权限表

**描述**：定义系统中的权限项。

| 字段名        | 类型           | 说明     | 约束说明           |
| ------------- | -------------- | -------- | ------------------ |
| `id`          | `BIGINT`       | 权限ID   | 主键, 自增         |
| `name`        | `VARCHAR(50)`  | 权限名称 | 非空               |
| `permission`  | `VARCHAR(100)` | 权限标识 | 唯一, 非空         |
| `description` | `VARCHAR(255)` | 权限描述 | 可空               |
| `create_time` | `DATETIME`     | 创建时间 | 非空, 默认当前时间 |
| `update_time` | `DATETIME`     | 更新时间 | 非空, 默认当前时间 |

**插入样例**：

```
INSERT INTO tb_permission (name, permission, description, create_time)
VALUES ('评论审核', 'comment:audit', '审核用户发布的评论', NOW());
```

#### 1.7 `tb_role_permission` - 角色权限关联表

**描述**：角色与权限的多对多关联。

| 字段名                                | 类型       | 说明       | 约束说明                   |
| ------------------------------------- | ---------- | ---------- | -------------------------- |
| `id`                                  | `BIGINT`   | ID         | 主键, 自增                 |
| `role_id`                             | `BIGINT`   | 角色ID     | 外键 -> `tb_role.id`       |
| `permission_id`                       | `BIGINT`   | 权限ID     | 外键 -> `tb_permission.id` |
| `create_time`                         | `DATETIME` | 创建时间   | 非空, 默认当前时间         |
| `UNIQUE KEY (role_id, permission_id)` |            | 复合唯一键 | 防止重复分配               |

**插入样例**：

```
INSERT INTO tb_role_permission (role_id, permission_id, create_time)
VALUES (1, 1, NOW());
```

