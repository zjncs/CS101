-- 1. 用户与权限模块 (User & Permissions)

-- 1.1 tb_user - 用户表
-- 描述：存储系统所有用户的基本信息
CREATE TABLE tb_user (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 用户ID, 主键, 自增
    username NVARCHAR(50) NOT NULL UNIQUE, -- 登录用户名, 唯一, 非空
    password NVARCHAR(100) NOT NULL, -- 密码（注意：原文 DES 加密不安全，建议使用哈希加盐存储）, 非空
    phone VARCHAR(20) UNIQUE NULL, -- 手机号, 唯一, 可空
    email NVARCHAR(100) UNIQUE NULL, -- 邮箱, 唯一, 可空
    nickname NVARCHAR(50) NOT NULL, -- 昵称, 非空
    icon NVARCHAR(255) NULL DEFAULT NULL, -- 头像URL, 可空, 默认系统头像 (这里用NULL代替默认值)
    gender TINYINT NOT NULL DEFAULT 2, -- 性别 (0=女, 1=男, 2=未知), 非空, 默认2
    birthday DATE NULL, -- 生日, 可空
    bio NVARCHAR(300) NULL, -- 个人简介, 可空
    campus NVARCHAR(50) NULL, -- 校区, 可空
    credits INT NOT NULL DEFAULT 0, -- 积分, 非空, 默认0
    level INT NOT NULL DEFAULT 1, -- 用户等级, 非空, 默认1
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=正常, 1=禁用), 非空, 默认0
    role TINYINT NOT NULL DEFAULT 0, -- 角色 (0=普通用户, 1=管理员, 2=系统管理者), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 1.2 tb_verification - 验证码表
-- 描述：存储短信或邮箱验证码
CREATE TABLE tb_verification (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 验证码ID, 主键, 自增
    phone VARCHAR(20) NULL, -- 手机号, 可空
    email NVARCHAR(100) NULL, -- 邮箱, 可空
    code VARCHAR(10) NOT NULL, -- 验证码, 非空
    type TINYINT NOT NULL, -- 验证类型 (0=注册, 1=登录, 2=找回密码), 非空
    expire_time DATETIME2 NOT NULL, -- 过期时间, 非空
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=未使用, 1=已使用, 2=已过期), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 创建时间, 非空, 默认当前时间
);
GO

-- 1.3 tb_admin - 管理员表
-- 描述：存储管理员的额外信息
CREATE TABLE tb_admin (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 管理员ID, 主键, 自增
    user_id BIGINT NOT NULL UNIQUE, -- 对应用户ID, 唯一, 非空
    name NVARCHAR(50) NOT NULL, -- 姓名, 非空
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=正常, 1=禁用), 非空, 默认0
    appointed_by BIGINT NULL, -- 任命管理员ID, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_admin_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_admin_appointed_by FOREIGN KEY (appointed_by) REFERENCES tb_admin(id) -- 外键 -> tb_admin.id (自引用)
);
GO

-- 1.4 tb_role - 角色表
-- 描述：定义系统中的角色
CREATE TABLE tb_role (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 角色ID, 主键, 自增
    name NVARCHAR(50) NOT NULL UNIQUE, -- 角色名称, 唯一, 非空
    description NVARCHAR(255) NULL, -- 角色描述, 可空
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=正常, 1=禁用), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 1.5 tb_admin_role - 管理员角色表
-- 描述：管理员与角色的多对多关联
CREATE TABLE tb_admin_role (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- ID, 主键, 自增
    admin_id BIGINT NOT NULL, -- 管理员ID
    role_id BIGINT NOT NULL, -- 角色ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT UQ_admin_role UNIQUE (admin_id, role_id), -- 复合唯一键, 防止重复分配
    CONSTRAINT FK_adminrole_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id), -- 外键 -> tb_admin.id
    CONSTRAINT FK_adminrole_role FOREIGN KEY (role_id) REFERENCES tb_role(id) -- 外键 -> tb_role.id
);
GO

-- 1.6 tb_permission - 权限表
-- 描述：定义系统中的权限项
CREATE TABLE tb_permission (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 权限ID, 主键, 自增
    name NVARCHAR(50) NOT NULL, -- 权限名称, 非空
    permission VARCHAR(100) NOT NULL UNIQUE, -- 权限标识, 唯一, 非空
    description NVARCHAR(255) NULL, -- 权限描述, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 1.7 tb_role_permission - 角色权限关联表
-- 描述：角色与权限的多对多关联
CREATE TABLE tb_role_permission (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- ID, 主键, 自增
    role_id BIGINT NOT NULL, -- 角色ID
    permission_id BIGINT NOT NULL, -- 权限ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT UQ_role_permission UNIQUE (role_id, permission_id), -- 复合唯一键, 防止重复分配
    CONSTRAINT FK_rolepermission_role FOREIGN KEY (role_id) REFERENCES tb_role(id), -- 外键 -> tb_role.id
    CONSTRAINT FK_rolepermission_permission FOREIGN KEY (permission_id) REFERENCES tb_permission(id) -- 外键 -> tb_permission.id
);
GO

-- 2. 基础数据模块 (Core Data)

-- 2.1 tb_campus - 校区表
-- 描述：记录学校的不同校区信息
CREATE TABLE tb_campus (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 校区ID, 主键, 自增
    name NVARCHAR(50) NOT NULL UNIQUE, -- 校区名称, 唯一, 非空
    address NVARCHAR(255) NULL, -- 地址, 可空
    x FLOAT NULL, -- 经度, 可空 (SQL Server 使用 FLOAT 对应 DOUBLE)
    y FLOAT NULL, -- 纬度, 可空 (SQL Server 使用 FLOAT 对应 DOUBLE)
    sort INT NOT NULL DEFAULT 0 -- 排序值, 非空, 默认0
);
GO

-- 2.2 tb_canteen_type - 食堂类型表
-- 描述：定义食堂的不同类型分类
CREATE TABLE tb_canteen_type (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 类型ID, 主键, 自增
    name NVARCHAR(50) NOT NULL UNIQUE, -- 类型名称, 唯一, 非空
    icon NVARCHAR(255) NULL, -- 图标URL, 可空
    sort INT NOT NULL DEFAULT 0 -- 排序值, 非空, 默认0
);
GO

-- 2.3 tb_canteen - 食堂表
-- 描述：存储各校区的食堂信息
CREATE TABLE tb_canteen (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 食堂ID, 主键, 自增
    name NVARCHAR(100) NOT NULL, -- 食堂名称, 非空
    campus_id BIGINT NOT NULL, -- 所属校区
    type_id BIGINT NOT NULL, -- 食堂类型ID
    images NVARCHAR(1024) NULL, -- 食堂图片，多个图片以,分隔, 可空
    address NVARCHAR(255) NOT NULL, -- 详细位置, 非空
    x FLOAT NULL, -- 经度, 可空
    y FLOAT NULL, -- 纬度, 可空
    floor NVARCHAR(20) NULL, -- 楼层信息, 可空
    open_hours NVARCHAR(255) NULL, -- 营业时间, 可空
    avg_price INT NULL, -- 人均价格, 可空
    introduction NVARCHAR(500) NULL, -- 食堂介绍, 可空
    score FLOAT NOT NULL DEFAULT 0, -- 综合评分, 非空, 默认0
    taste_score FLOAT NOT NULL DEFAULT 0, -- 口味评分, 非空, 默认0
    environment_score FLOAT NOT NULL DEFAULT 0, -- 环境评分, 非空, 默认0
    service_score FLOAT NOT NULL DEFAULT 0, -- 服务评分, 非空, 默认0
    liked INT NOT NULL DEFAULT 0, -- 点赞数, 非空, 默认0
    comments INT NOT NULL DEFAULT 0, -- 评论数, 非空, 默认0
    open_status TINYINT NOT NULL DEFAULT 1, -- 营业状态 (0=休息, 1=营业), 非空, 默认1
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_canteen_campus FOREIGN KEY (campus_id) REFERENCES tb_campus(id), -- 外键 -> tb_campus.id
    CONSTRAINT FK_canteen_type FOREIGN KEY (type_id) REFERENCES tb_canteen_type(id) -- 外键 -> tb_canteen_type.id
);
GO

-- 2.4 tb_stall_type - 窗口类型表
-- 描述：定义食堂窗口的不同类型分类
CREATE TABLE tb_stall_type (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 类型ID, 主键, 自增
    name NVARCHAR(50) NOT NULL UNIQUE, -- 类型名称, 唯一, 非空
    icon NVARCHAR(255) NULL, -- 图标URL, 可空
    sort INT NOT NULL DEFAULT 0 -- 排序值, 非空, 默认0
);
GO

-- 2.5 tb_stall - 窗口表
-- 描述：存储食堂中各个窗口的信息
CREATE TABLE tb_stall (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 窗口ID, 主键, 自增
    name NVARCHAR(100) NOT NULL, -- 窗口名称, 非空
    canteen_id BIGINT NOT NULL, -- 所属食堂ID
    type_id BIGINT NOT NULL, -- 窗口类型ID
    images NVARCHAR(1024) NULL, -- 窗口图片，多个图片以,分隔, 可空
    location NVARCHAR(50) NULL, -- 窗口位置编号, 可空
    introduction NVARCHAR(500) NULL, -- 窗口介绍, 可空
    open_hours NVARCHAR(255) NULL, -- 营业时间, 可空
    score FLOAT NOT NULL DEFAULT 0, -- 综合评分, 非空, 默认0
    taste_score FLOAT NOT NULL DEFAULT 0, -- 口味评分, 非空, 默认0
    price_score FLOAT NOT NULL DEFAULT 0, -- 性价比评分, 非空, 默认0
    comments INT NOT NULL DEFAULT 0, -- 评论数, 非空, 默认0
    open_status TINYINT NOT NULL DEFAULT 1, -- 营业状态 (0=休息, 1=营业), 非空, 默认1
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_stall_canteen FOREIGN KEY (canteen_id) REFERENCES tb_canteen(id), -- 外键 -> tb_canteen.id
    CONSTRAINT FK_stall_type FOREIGN KEY (type_id) REFERENCES tb_stall_type(id) -- 外键 -> tb_stall_type.id
);
GO

-- 2.6 tb_dish - 菜品表
-- 描述：存储窗口提供的菜品信息
CREATE TABLE tb_dish (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 菜品ID, 主键, 自增
    name NVARCHAR(100) NOT NULL, -- 菜品名称, 非空
    stall_id BIGINT NOT NULL, -- 所属窗口ID
    category NVARCHAR(50) NULL, -- 菜品分类, 可空
    price DECIMAL(10, 2) NOT NULL, -- 价格, 非空
    description NVARCHAR(500) NULL, -- 菜品描述, 可空
    images NVARCHAR(1024) NULL, -- 菜品图片，多个图片以,分隔, 可空
    nutrition NVARCHAR(500) NULL, -- 营养成分(JSON字符串), 可空
    is_special TINYINT NOT NULL DEFAULT 0, -- 是否招牌菜 (0=否, 1=是), 非空, 默认0
    is_limited TINYINT NOT NULL DEFAULT 0, -- 是否限量 (0=否, 1=是), 非空, 默认0
    score FLOAT NOT NULL DEFAULT 0, -- 评分, 非空, 默认0
    comments INT NOT NULL DEFAULT 0, -- 评论提及次数, 非空, 默认0
    status TINYINT NOT NULL DEFAULT 1, -- 状态 (0=下架, 1=上架), 非空, 默认1
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_dish_stall FOREIGN KEY (stall_id) REFERENCES tb_stall(id) -- 外键 -> tb_stall.id
);
GO

-- 3. 内容与互动模块 (Content & Interaction)

-- 3.1 tb_blog - 博客/笔记表
-- 描述：存储用户发布的美食博客或笔记内容
CREATE TABLE tb_blog (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 博客ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 作者ID
    title NVARCHAR(200) NULL, -- 标题, 可空
    content NVARCHAR(2000) NOT NULL, -- 正文内容, 非空
    images NVARCHAR(1024) NULL, -- 图片，多个图片以,分隔, 可空
    canteen_id BIGINT NULL, -- 关联食堂ID, 可空
    stall_id BIGINT NULL, -- 关联窗口ID, 可空
    liked INT NOT NULL DEFAULT 0, -- 点赞数, 非空, 默认0
    comments INT NOT NULL DEFAULT 0, -- 评论数, 非空, 默认0
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=正常, 1=隐藏), 非空, 默认0
    is_top TINYINT NOT NULL DEFAULT 0, -- 是否置顶 (0=否, 1=是), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_blog_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_blog_canteen FOREIGN KEY (canteen_id) REFERENCES tb_canteen(id), -- 外键 -> tb_canteen.id
    CONSTRAINT FK_blog_stall FOREIGN KEY (stall_id) REFERENCES tb_stall(id) -- 外键 -> tb_stall.id
);
GO

-- 3.2 tb_blog_dish - 博客菜品关联表
-- 描述：博客与菜品的多对多关联
CREATE TABLE tb_blog_dish (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 关联ID, 主键, 自增
    blog_id BIGINT NOT NULL, -- 博客ID
    dish_id BIGINT NOT NULL, -- 菜品ID
    is_recommended TINYINT NOT NULL DEFAULT 1, -- 是否推荐 (0=否, 1=是), 非空, 默认1
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT UQ_blog_dish UNIQUE (blog_id, dish_id), -- 复合唯一键, 防止重复关联
    CONSTRAINT FK_blogdish_blog FOREIGN KEY (blog_id) REFERENCES tb_blog(id), -- 外键 -> tb_blog.id
    CONSTRAINT FK_blogdish_dish FOREIGN KEY (dish_id) REFERENCES tb_dish(id) -- 外键 -> tb_dish.id
);
GO

-- 3.3 tb_topic - 话题表
-- 描述：存储系统中的话题
CREATE TABLE tb_topic (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 话题ID, 主键, 自增
    name NVARCHAR(100) NOT NULL UNIQUE, -- 话题名称, 唯一, 非空
    description NVARCHAR(500) NULL, -- 话题描述, 可空
    cover NVARCHAR(255) NULL, -- 封面图URL, 可空
    sort INT NOT NULL DEFAULT 0, -- 排序值, 非空, 默认0
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=正常, 1=隐藏), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 3.4 tb_blog_topic - 博客话题关联表
-- 描述：博客与话题的多对多关联
CREATE TABLE tb_blog_topic (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 关联ID, 主键, 自增
    blog_id BIGINT NOT NULL, -- 博客ID
    topic_id BIGINT NOT NULL, -- 话题ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT UQ_blog_topic UNIQUE (blog_id, topic_id), -- 复合唯一键, 防止重复关联
    CONSTRAINT FK_blogtopic_blog FOREIGN KEY (blog_id) REFERENCES tb_blog(id), -- 外键 -> tb_blog.id
    CONSTRAINT FK_blogtopic_topic FOREIGN KEY (topic_id) REFERENCES tb_topic(id) -- 外键 -> tb_topic.id
);
GO

-- 3.5 tb_tag - 标签表
-- 描述：存储系统中的标签
CREATE TABLE tb_tag (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 标签ID, 主键, 自增
    name NVARCHAR(50) NOT NULL UNIQUE, -- 标签名称, 唯一, 非空
    usage_count INT NOT NULL DEFAULT 0, -- 使用次数, 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 创建时间, 非空, 默认当前时间
);
GO

-- 3.6 tb_blog_tag - 博客标签关联表
-- 描述：博客与标签的多对多关联
CREATE TABLE tb_blog_tag (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 关联ID, 主键, 自增
    blog_id BIGINT NOT NULL, -- 博客ID
    tag_id BIGINT NOT NULL, -- 标签ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT UQ_blog_tag UNIQUE (blog_id, tag_id), -- 复合唯一键, 防止重复关联
    CONSTRAINT FK_blogtag_blog FOREIGN KEY (blog_id) REFERENCES tb_blog(id), -- 外键 -> tb_blog.id
    CONSTRAINT FK_blogtag_tag FOREIGN KEY (tag_id) REFERENCES tb_tag(id) -- 外键 -> tb_tag.id
);
GO

-- 3.7 tb_review - 评价表
-- 描述：存储用户对食堂、窗口或菜品的评价内容和评分
CREATE TABLE tb_review (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 评价ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    content NVARCHAR(1024) NOT NULL, -- 评价内容, 非空
    images NVARCHAR(1024) NULL, -- 图片，多个图片以,分隔, 可空
    canteen_id BIGINT NULL, -- 评价食堂ID, 可空
    stall_id BIGINT NULL, -- 评价窗口ID, 可空
    dish_id BIGINT NULL, -- 评价菜品ID, 可空
    overall_score INT NOT NULL CHECK (overall_score BETWEEN 1 AND 5), -- 总体评分(1-5), 非空
    taste_score INT NULL CHECK (taste_score BETWEEN 1 AND 5), -- 口味评分(1-5), 可空
    environment_score INT NULL CHECK (environment_score BETWEEN 1 AND 5), -- 环境评分(1-5), 可空
    service_score INT NULL CHECK (service_score BETWEEN 1 AND 5), -- 服务评分(1-5), 可空
    price_score INT NULL CHECK (price_score BETWEEN 1 AND 5), -- 性价比评分(1-5), 可空
    liked INT NOT NULL DEFAULT 0, -- 点赞数, 非空, 默认0
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=待审核, 1=已通过, 2=已拒绝), 非空, 默认0
    auditor_id BIGINT NULL, -- 审核管理员ID, 可空
    audit_time DATETIME2 NULL, -- 审核时间, 可空
    audit_note NVARCHAR(255) NULL, -- 审核备注, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_review_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_review_canteen FOREIGN KEY (canteen_id) REFERENCES tb_canteen(id), -- 外键 -> tb_canteen.id
    CONSTRAINT FK_review_stall FOREIGN KEY (stall_id) REFERENCES tb_stall(id), -- 外键 -> tb_stall.id
    CONSTRAINT FK_review_dish FOREIGN KEY (dish_id) REFERENCES tb_dish(id), -- 外键 -> tb_dish.id
    CONSTRAINT FK_review_auditor FOREIGN KEY (auditor_id) REFERENCES tb_admin(id) -- 外键 -> tb_admin.id
);
GO

-- 3.8 tb_comment - 评论表
-- 描述：存储用户对博客或评价的评论
CREATE TABLE tb_comment (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 评论ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    blog_id BIGINT NULL, -- 博客ID, 可空
    review_id BIGINT NULL, -- 评价ID, 可空
    parent_id BIGINT NULL, -- 父评论ID, 可空 (自引用)
    content NVARCHAR(255) NOT NULL, -- 评论内容, 非空
    liked INT NOT NULL DEFAULT 0, -- 点赞数, 非空, 默认0
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=待审核, 1=已通过, 2=已拒绝), 非空, 默认0
    auditor_id BIGINT NULL, -- 审核管理员ID, 可空
    audit_time DATETIME2 NULL, -- 审核时间, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT FK_comment_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_comment_blog FOREIGN KEY (blog_id) REFERENCES tb_blog(id), -- 外键 -> tb_blog.id
    CONSTRAINT FK_comment_review FOREIGN KEY (review_id) REFERENCES tb_review(id), -- 外键 -> tb_review.id
    CONSTRAINT FK_comment_parent FOREIGN KEY (parent_id) REFERENCES tb_comment(id), -- 外键 -> tb_comment.id
    CONSTRAINT FK_comment_auditor FOREIGN KEY (auditor_id) REFERENCES tb_admin(id) -- 外键 -> tb_admin.id
);
GO

-- 3.9 tb_like - 点赞表
-- 描述：记录用户对博客、评价或评论的点赞
CREATE TABLE tb_like (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 点赞ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    liked_id BIGINT NOT NULL, -- 被点赞对象ID, 非空
    type TINYINT NOT NULL, -- 点赞对象类型 (1=博客, 2=评价, 3=评论), 非空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 点赞时间, 非空, 默认当前时间
    CONSTRAINT UQ_like UNIQUE (user_id, liked_id, type), -- 复合唯一键, 防止重复点赞
    CONSTRAINT FK_like_user FOREIGN KEY (user_id) REFERENCES tb_user(id) -- 外键 -> tb_user.id
);
GO

-- 3.10 tb_favorite - 收藏表
-- 描述：记录用户对博客、评价、食堂、窗口或菜品的收藏
CREATE TABLE tb_favorite (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 收藏ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    favorite_id BIGINT NOT NULL, -- 被收藏对象ID, 非空
    type TINYINT NOT NULL, -- 收藏对象类型 (1=博客, 2=评价, 3=食堂, 4=窗口, 5=菜品), 非空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 收藏时间, 非空, 默认当前时间
    CONSTRAINT UQ_favorite UNIQUE (user_id, favorite_id, type), -- 复合唯一键, 防止重复收藏
    CONSTRAINT FK_favorite_user FOREIGN KEY (user_id) REFERENCES tb_user(id) -- 外键 -> tb_user.id
);
GO

-- 3.11 tb_follow - 关注表
-- 描述：记录用户间的关注关系
CREATE TABLE tb_follow (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 关系ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 关注人ID
    follow_user_id BIGINT NOT NULL, -- 被关注人ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 关注时间, 非空, 默认当前时间
    CONSTRAINT UQ_follow UNIQUE (user_id, follow_user_id), -- 复合唯一键, 防止重复关注
    CONSTRAINT FK_follow_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_follow_following FOREIGN KEY (follow_user_id) REFERENCES tb_user(id) -- 外键 -> tb_user.id
);
GO

-- 3.12 tb_message - 消息表
-- 描述：存储系统中的消息通知
CREATE TABLE tb_message (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 消息ID, 主键, 自增
    from_user_id BIGINT NULL, -- 发送者ID, 可空 (系统消息时为空)
    to_user_id BIGINT NOT NULL, -- 接收者ID
    type TINYINT NOT NULL, -- 消息类型 (0=系统消息, 1=点赞, 2=评论, 3=关注), 非空
    source_id BIGINT NULL, -- 来源ID (如博客ID、评论ID), 可空
    source_type TINYINT NULL, -- 来源类型, 可空
    content NVARCHAR(255) NULL, -- 消息内容, 可空
    read_status TINYINT NOT NULL DEFAULT 0, -- 已读状态 (0=未读, 1=已读), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT FK_message_from_user FOREIGN KEY (from_user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_message_to_user FOREIGN KEY (to_user_id) REFERENCES tb_user(id) -- 外键 -> tb_user.id
);
GO

-- 3.13 tb_search_history - 搜索历史表
-- 描述：记录用户的搜索关键词
CREATE TABLE tb_search_history (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 历史ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    keyword NVARCHAR(100) NOT NULL, -- 关键词, 非空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 搜索时间, 非空, 默认当前时间
    CONSTRAINT FK_searchhistory_user FOREIGN KEY (user_id) REFERENCES tb_user(id) -- 外键 -> tb_user.id
);
GO

-- 4. 用户激励与活动模块 (User Engagement & Activities)

-- 4.1 tb_credit_record - 积分记录表
-- 描述：记录用户积分的变动情况
CREATE TABLE tb_credit_record (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 记录ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    type NVARCHAR(50) NOT NULL, -- 积分类型, 非空
    credits INT NOT NULL, -- 积分变动值, 非空
    description NVARCHAR(255) NULL, -- 积分描述, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT FK_creditrecord_user FOREIGN KEY (user_id) REFERENCES tb_user(id) -- 外键 -> tb_user.id
);
GO

-- 4.2 tb_sign - 签到表
-- 描述：记录用户的签到情况
CREATE TABLE tb_sign (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 签到ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    year INT NOT NULL, -- 签到年份, 非空
    month INT NOT NULL, -- 签到月份, 非空
    date DATE NOT NULL, -- 签到日期, 非空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT UQ_sign UNIQUE (user_id, date), -- 复合唯一键, 防止重复签到
    CONSTRAINT FK_sign_user FOREIGN KEY (user_id) REFERENCES tb_user(id) -- 外键 -> tb_user.id
);
GO

-- 4.3 tb_medal - 勋章表
-- 描述：定义系统中的勋章
CREATE TABLE tb_medal (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 勋章ID, 主键, 自增
    name NVARCHAR(50) NOT NULL UNIQUE, -- 勋章名称, 唯一, 非空
    description NVARCHAR(255) NOT NULL, -- 勋章描述, 非空
    icon NVARCHAR(255) NOT NULL, -- 勋章图标URL, 非空
    condition NVARCHAR(255) NOT NULL, -- 获取条件, 非空
    type TINYINT NOT NULL DEFAULT 0, -- 勋章类型 (0=普通, 1=活动, 2=成就), 非空, 默认0
    credits INT NOT NULL DEFAULT 0, -- 获得积分, 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 4.4 tb_user_medal - 用户勋章表
-- 描述：记录用户获得的勋章
CREATE TABLE tb_user_medal (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    medal_id BIGINT NOT NULL, -- 勋章ID
    obtain_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 获得时间, 非空, 默认当前时间
    is_display TINYINT NOT NULL DEFAULT 1, -- 是否展示 (0=否, 1=是), 非空, 默认1
    CONSTRAINT UQ_user_medal UNIQUE (user_id, medal_id), -- 复合唯一键, 防止重复获得
    CONSTRAINT FK_usermedal_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_usermedal_medal FOREIGN KEY (medal_id) REFERENCES tb_medal(id) -- 外键 -> tb_medal.id
);
GO

-- 4.5 tb_voucher - 优惠券表
-- 描述：定义系统中的优惠券
CREATE TABLE tb_voucher (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 优惠券ID, 主键, 自增
    title NVARCHAR(100) NOT NULL, -- 优惠券标题, 非空
    description NVARCHAR(255) NULL, -- 优惠券描述, 可空
    type TINYINT NOT NULL DEFAULT 0, -- 优惠券类型 (0=满减券, 1=折扣券), 非空, 默认0
    price DECIMAL(10, 2) NULL, -- 优惠券金额(满减券), 可空
    discount DECIMAL(5, 2) NULL, -- 折扣率(0.1-1折扣券), 可空
    min_amount DECIMAL(10, 2) NOT NULL DEFAULT 0, -- 最低消费金额, 非空, 默认0
    canteen_id BIGINT NULL, -- 限定食堂ID, 可空
    stall_id BIGINT NULL, -- 限定窗口ID, 可空
    start_time DATETIME2 NOT NULL, -- 生效时间, 非空
    end_time DATETIME2 NOT NULL, -- 过期时间, 非空
    stock INT NOT NULL DEFAULT 0, -- 库存, 非空, 默认0
    required_credits INT NOT NULL DEFAULT 0, -- 兑换所需积分, 非空, 默认0
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=未开始, 1=进行中, 2=已结束, 3=已下线), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_voucher_canteen FOREIGN KEY (canteen_id) REFERENCES tb_canteen(id), -- 外键 -> tb_canteen.id
    CONSTRAINT FK_voucher_stall FOREIGN KEY (stall_id) REFERENCES tb_stall(id) -- 外键 -> tb_stall.id
);
GO

-- 4.6 tb_user_voucher - 用户优惠券表
-- 描述：记录用户持有的优惠券
CREATE TABLE tb_user_voucher (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 用户券ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    voucher_id BIGINT NOT NULL, -- 优惠券ID
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=未使用, 1=已使用, 2=已过期), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 领取时间, 非空, 默认当前时间
    use_time DATETIME2 NULL, -- 使用时间, 可空
    CONSTRAINT FK_uservoucher_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_uservoucher_voucher FOREIGN KEY (voucher_id) REFERENCES tb_voucher(id) -- 外键 -> tb_voucher.id
);
GO

-- 4.7 tb_level_rule - 等级规则表
-- 描述：定义用户等级晋升规则和特权
CREATE TABLE tb_level_rule (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 规则ID, 主键, 自增
    level INT NOT NULL UNIQUE, -- 等级, 唯一, 非空
    name NVARCHAR(50) NOT NULL, -- 等级名称, 非空
    min_credits INT NOT NULL, -- 最低积分要求, 非空
    max_credits INT NOT NULL, -- 最高积分限制, 非空
    benefits NVARCHAR(500) NULL, -- 等级特权(JSON字符串), 可空
    icon NVARCHAR(255) NULL, -- 等级图标URL, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 4.8 tb_activity - 活动表
-- 描述：存储系统发布的各类活动信息
CREATE TABLE tb_activity (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 活动ID, 主键, 自增
    title NVARCHAR(100) NOT NULL, -- 活动标题, 非空
    description NVARCHAR(500) NOT NULL, -- 活动描述, 非空
    cover NVARCHAR(255) NULL, -- 封面图URL, 可空
    type TINYINT NOT NULL, -- 活动类型 (0=普通活动, 1=积分活动, 2=社区活动), 非空
    start_time DATETIME2 NOT NULL, -- 开始时间, 非空
    end_time DATETIME2 NOT NULL, -- 结束时间, 非空
    rules NVARCHAR(MAX) NOT NULL, -- 活动规则 (原文 TEXT), 非空
    rewards NVARCHAR(500) NOT NULL, -- 活动奖励, 非空
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=未开始, 1=进行中, 2=已结束, 3=已取消), 非空, 默认0
    admin_id BIGINT NOT NULL, -- 发布管理员ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_activity_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id) -- 外键 -> tb_admin.id
);
GO

-- 4.9 tb_user_activity - 用户活动参与表
-- 描述：记录用户参与活动的情况
CREATE TABLE tb_user_activity (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 参与ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    activity_id BIGINT NOT NULL, -- 活动ID
    join_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 参与时间, 非空, 默认当前时间
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=参与中, 1=已完成, 2=已放弃), 非空, 默认0
    progress INT NOT NULL DEFAULT 0, -- 完成进度 (百分比), 非空, 默认0
    reward_status TINYINT NOT NULL DEFAULT 0, -- 奖励状态 (0=未发放, 1=已发放), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT UQ_user_activity UNIQUE (user_id, activity_id), -- 复合唯一键, 防止重复参与
    CONSTRAINT FK_useractivity_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_useractivity_activity FOREIGN KEY (activity_id) REFERENCES tb_activity(id) -- 外键 -> tb_activity.id
);
GO

-- 5. 系统管理与支持模块 (System Management & Support)

-- 5.1 tb_config - 系统配置表
-- 描述：存储系统的各项配置参数
CREATE TABLE tb_config (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 配置ID, 主键, 自增
    [key] NVARCHAR(100) NOT NULL UNIQUE, -- 配置键 (key 是 SQL Server 关键字，用方括号括起来), 唯一, 非空
    value NVARCHAR(1000) NOT NULL, -- 配置值, 非空
    description NVARCHAR(255) NULL, -- 描述, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 5.2 tb_sensitive_word - 敏感词表
-- 描述：存储系统敏感词过滤配置
CREATE TABLE tb_sensitive_word (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 配置ID, 主键, 自增
    word NVARCHAR(100) NOT NULL UNIQUE, -- 敏感词, 唯一, 非空
    type TINYINT NOT NULL DEFAULT 0, -- 类型 (0=普通, 1=政治, 2=色情, 3=暴力), 非空, 默认0
    replace_word NVARCHAR(100) NULL, -- 替换词, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 5.3 tb_log - 系统日志表
-- 描述：记录系统操作日志
CREATE TABLE tb_log (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 日志ID, 主键, 自增
    user_id BIGINT NULL, -- 用户ID, 可空
    admin_id BIGINT NULL, -- 管理员ID, 可空
    operation NVARCHAR(100) NOT NULL, -- 操作类型, 非空
    method NVARCHAR(255) NULL, -- 请求方法, 可空
    params NVARCHAR(1000) NULL, -- 请求参数, 可空
    ip VARCHAR(50) NULL, -- IP地址, 可空
    user_agent NVARCHAR(255) NULL, -- 用户代理, 可空
    operation_time INT NULL, -- 执行时长(毫秒), 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 创建时间, 非空, 默认当前时间
);
GO

-- 5.4 tb_notice - 系统公告表
-- 描述：存储系统公告信息
CREATE TABLE tb_notice (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 公告ID, 主键, 自增
    admin_id BIGINT NOT NULL, -- 发布管理员ID
    title NVARCHAR(100) NOT NULL, -- 公告标题, 非空
    content NVARCHAR(MAX) NOT NULL, -- 公告内容 (原文 TEXT), 非空
    type TINYINT NOT NULL DEFAULT 0, -- 公告类型 (0=普通公告, 1=系统公告, 2=活动公告), 非空, 默认0
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=未发布, 1=已发布, 2=已下线), 非空, 默认0
    start_time DATETIME2 NULL, -- 生效时间, 可空
    end_time DATETIME2 NULL, -- 结束时间, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_notice_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id) -- 外键 -> tb_admin.id
);
GO

-- 5.5 tb_version - 版本管理表
-- 描述：存储系统版本信息
CREATE TABLE tb_version (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 版本ID, 主键, 自增
    version NVARCHAR(50) NOT NULL, -- 版本号, 非空
    platform TINYINT NOT NULL DEFAULT 0, -- 平台 (0=全部, 1=Android, 2=iOS, 3=Web), 非空, 默认0
    description NVARCHAR(1000) NOT NULL, -- 版本描述, 非空
    download_url NVARCHAR(255) NULL, -- 下载链接, 可空
    is_force TINYINT NOT NULL DEFAULT 0, -- 是否强制更新 (0=否, 1=是), 非空, 默认0
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=未发布, 1=已发布), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 5.6 tb_feedback - 反馈表
-- 描述：存储用户提交的反馈意见
CREATE TABLE tb_feedback (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 反馈ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    type TINYINT NOT NULL, -- 反馈类型 (0=功能建议, 1=内容举报, 2=其他), 非空
    content NVARCHAR(500) NOT NULL, -- 反馈内容, 非空
    contact NVARCHAR(100) NULL, -- 联系方式, 可空
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=未处理, 1=已处理), 非空, 默认0
    response NVARCHAR(500) NULL, -- 回复内容, 可空
    admin_id BIGINT NULL, -- 处理管理员ID, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_feedback_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_feedback_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id) -- 外键 -> tb_admin.id
);
GO

-- 5.7 tb_report - 举报表
-- 描述：存储用户举报内容
CREATE TABLE tb_report (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 举报ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 举报用户ID
    target_id BIGINT NOT NULL, -- 举报目标ID, 非空
    type TINYINT NOT NULL, -- 举报类型 (0=博客, 1=评价, 2=评论, 3=用户), 非空
    reason NVARCHAR(255) NOT NULL, -- 举报原因, 非空
    description NVARCHAR(500) NULL, -- 举报描述, 可空
    images NVARCHAR(1024) NULL, -- 图片，多个图片以,分隔, 可空
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=未处理, 1=已处理, 2=已驳回), 非空, 默认0
    handle_admin_id BIGINT NULL, -- 处理管理员ID, 可空
    handle_note NVARCHAR(255) NULL, -- 处理备注, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_report_user FOREIGN KEY (user_id) REFERENCES tb_user(id), -- 外键 -> tb_user.id
    CONSTRAINT FK_report_admin FOREIGN KEY (handle_admin_id) REFERENCES tb_admin(id) -- 外键 -> tb_admin.id
);
GO

-- 6. 媒体与展示模块 (Media & Display)

-- 6.1 tb_file - 文件表
-- 描述：存储系统中上传的文件信息（用户头像、博客图片、评价图片等）
CREATE TABLE tb_file (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 文件ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 上传者ID
    name NVARCHAR(255) NOT NULL, -- 文件名, 非空
    original_name NVARCHAR(255) NOT NULL, -- 原始文件名, 非空
    url NVARCHAR(255) NOT NULL, -- 文件URL, 非空
    type VARCHAR(50) NOT NULL, -- 文件类型, 非空
    size INT NOT NULL, -- 文件大小(字节), 非空
    width INT NULL, -- 图片宽度, 可空
    height INT NULL, -- 图片高度, 可空
    duration INT NULL, -- 媒体时长(秒), 可空
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=正常, 1=已删除), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT FK_file_user FOREIGN KEY (user_id) REFERENCES tb_user(id) -- 外键 -> tb_user.id
);
GO

-- 6.2 tb_banner - 轮播图表
-- 描述：存储首页轮播图信息
CREATE TABLE tb_banner (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 轮播图ID, 主键, 自增
    title NVARCHAR(100) NOT NULL, -- 标题, 非空
    image NVARCHAR(255) NOT NULL, -- 图片URL, 非空
    link NVARCHAR(255) NULL, -- 跳转链接, 可空
    target_id BIGINT NULL, -- 目标ID, 可空
    target_type TINYINT NULL, -- 目标类型 (0=无, 1=食堂, 2=窗口, 3=博客, 4=话题), 可空
    sort INT NOT NULL DEFAULT 0, -- 排序值, 非空, 默认0
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=正常, 1=下线), 非空, 默认0
    start_time DATETIME2 NULL, -- 开始时间, 可空
    end_time DATETIME2 NULL, -- 结束时间, 可空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 6.3 tb_image_category - 图片分类表
-- 描述：定义系统管理图片的分类（如食堂环境图等）
CREATE TABLE tb_image_category (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 分类ID, 主键, 自增
    name NVARCHAR(50) NOT NULL, -- 分类名称, 非空
    description NVARCHAR(255) NULL, -- 分类描述, 可空
    sort INT NOT NULL DEFAULT 0, -- 排序值, 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 更新时间, 非空, 默认当前时间
);
GO

-- 6.4 tb_image - 图片管理表
-- 描述：存储由系统管理员上传和管理的图片信息
CREATE TABLE tb_image (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 图片ID, 主键, 自增
    category_id BIGINT NOT NULL, -- 分类ID
    admin_id BIGINT NOT NULL, -- 上传管理员ID
    name NVARCHAR(100) NOT NULL, -- 图片名称, 非空
    url NVARCHAR(255) NOT NULL, -- 图片URL, 非空
    width INT NOT NULL, -- 图片宽度, 非空
    height INT NOT NULL, -- 图片高度, 非空
    size INT NOT NULL, -- 图片大小(字节), 非空
    description NVARCHAR(255) NULL, -- 图片描述, 可空
    status TINYINT NOT NULL DEFAULT 0, -- 状态 (0=正常, 1=已删除), 非空, 默认0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_image_category FOREIGN KEY (category_id) REFERENCES tb_image_category(id), -- 外键 -> tb_image_category.id
    CONSTRAINT FK_image_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id) -- 外键 -> tb_admin.id
);
GO

-- 7. 统计与分析模块 (Statistics & Analysis)

-- 7.1 tb_ranking - 排行榜表
-- 描述：存储系统中各类排行榜数据
CREATE TABLE tb_ranking (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 排行ID, 主键, 自增
    type TINYINT NOT NULL, -- 排行类型 (0=菜品, 1=食堂, 2=窗口, 3=用户积分), 非空
    target_id BIGINT NOT NULL, -- 目标ID, 非空
    score FLOAT NOT NULL, -- 评分/数值 (原文 DOUBLE), 非空
    rank INT NOT NULL, -- 排名, 非空
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT UQ_ranking UNIQUE (type, target_id) -- 复合唯一键, 防止重复
);
GO

-- 7.2 tb_statistics - 统计数据表
-- 描述：存储系统各类统计数据
CREATE TABLE tb_statistics (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 统计ID, 主键, 自增
    type TINYINT NOT NULL, -- 统计类型 (0=用户活跃度, 1=评分分布, 2=评论热度), 非空
    target_id BIGINT NULL, -- 目标ID (可以是食堂/窗口/菜品ID), 可空
    period VARCHAR(20) NOT NULL, -- 统计周期 (day/week/month/year), 非空
    date DATE NOT NULL, -- 统计日期, 非空
    data NVARCHAR(MAX) NOT NULL, -- 统计数据(JSON字符串) (原文 TEXT), 非空
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    CONSTRAINT UQ_statistics UNIQUE (type, target_id, period, date) -- 复合唯一键, 防止重复
);
GO

-- 7.3 tb_user_preference - 用户偏好表
-- 描述：记录用户的偏好信息，用于个性化推荐
CREATE TABLE tb_user_preference (
    id BIGINT PRIMARY KEY IDENTITY(1,1), -- 偏好ID, 主键, 自增
    user_id BIGINT NOT NULL, -- 用户ID
    preference_type TINYINT NOT NULL, -- 偏好类型 (0=口味, 1=价格, 2=窗口类型), 非空
    preference_value NVARCHAR(100) NOT NULL, -- 偏好值, 非空
    weight FLOAT NOT NULL DEFAULT 1.0, -- 权重值 (原文 DOUBLE), 非空, 默认1.0
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 创建时间, 非空, 默认当前时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 更新时间, 非空, 默认当前时间
    CONSTRAINT FK_userpreference_user FOREIGN KEY (user_id) REFERENCES tb_user(id) -- 外键 -> tb_user.id
);
GO