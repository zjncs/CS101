-- 1.1 tb_user - 普通用户表
CREATE TABLE tb_user (
    id BIGINT PRIMARY KEY IDENTITY(1,1),                -- 用户ID
    password NVARCHAR(100) NOT NULL,                    -- 密码 (加密存储)
    email NVARCHAR(100) UNIQUE NOT NULL,               -- 邮箱 (用于登录和验证)
    phone VARCHAR(20) NULL,                            -- 手机号
    nickname NVARCHAR(50) NOT NULL,                      -- 昵称
    icon NVARCHAR(255) NULL,                           -- 头像URL
    gender TINYINT NOT NULL DEFAULT 2,                   -- 性别 (0=女, 1=男, 2=未知)
    birthday DATE NULL,                                -- 生日
    bio NVARCHAR(300) NULL,                            -- 个人简介
    campus NVARCHAR(50) NOT NULL,                        -- 所属校区
    credits INT NOT NULL DEFAULT 0,                      -- 积分
    level INT NOT NULL DEFAULT 1,                        -- 用户等级
    status TINYINT NOT NULL DEFAULT 0,                   -- 状态 (0=正常, 1=禁用)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),    -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()     -- 更新时间
);
GO

-- 1.2 tb_admin - 管理员表
CREATE TABLE tb_admin (
    id BIGINT PRIMARY KEY IDENTITY(1,1),                -- 管理员ID
    username NVARCHAR(50) NOT NULL UNIQUE,             -- 管理员登录账号
    password NVARCHAR(100) NOT NULL,                    -- 管理员密码 (加密存储)
    name NVARCHAR(50) NOT NULL,                        -- 真实姓名或职位
    status TINYINT NOT NULL DEFAULT 0,                   -- 状态 (0=正常, 1=禁用)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 1.3 tb_role - 角色表
CREATE TABLE tb_role (
    id BIGINT PRIMARY KEY IDENTITY(1,1),                -- 角色ID
    name NVARCHAR(50) NOT NULL UNIQUE,                 -- 角色名称
    description NVARCHAR(255) NULL,                    -- 角色描述
    status TINYINT NOT NULL DEFAULT 0,                   -- 状态 (0=正常, 1=禁用)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 1.4 tb_admin_role - 管理员角色关联表
CREATE TABLE tb_admin_role (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    admin_id BIGINT NOT NULL,                            -- 管理员ID
    role_id BIGINT NOT NULL,                             -- 角色ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_admin_role UNIQUE (admin_id, role_id),
    CONSTRAINT FK_adminrole_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id) ON DELETE CASCADE,
    CONSTRAINT FK_adminrole_role FOREIGN KEY (role_id) REFERENCES tb_role(id) ON DELETE CASCADE
);
GO

-- 1.5 tb_permission - 权限表
CREATE TABLE tb_permission (
    id BIGINT PRIMARY KEY IDENTITY(1,1),                -- 权限ID
    name NVARCHAR(50) NOT NULL,                        -- 权限名称 (如: "新增菜品")
    permission_key VARCHAR(100) NOT NULL UNIQUE,       -- 权限标识 (如: "dish:create")
    description NVARCHAR(255) NULL,                    -- 权限描述
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 1.6 tb_role_permission - 角色权限关联表
CREATE TABLE tb_role_permission (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    role_id BIGINT NOT NULL,                             -- 角色ID
    permission_id BIGINT NOT NULL,                       -- 权限ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_role_permission UNIQUE (role_id, permission_id),
    CONSTRAINT FK_rolepermission_role FOREIGN KEY (role_id) REFERENCES tb_role(id) ON DELETE CASCADE,
    CONSTRAINT FK_rolepermission_permission FOREIGN KEY (permission_id) REFERENCES tb_permission(id) ON DELETE CASCADE
);
GO
-- 2.1 tb_campus - 校区表
CREATE TABLE tb_campus (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 校区ID
    name NVARCHAR(50) NOT NULL UNIQUE,         -- 校区名称
    address NVARCHAR(255) NULL,                -- 地址
    x FLOAT NULL,                              -- 经度
    y FLOAT NULL                               -- 纬度
);
GO

-- 2.2 tb_canteen_type - 食堂类型表
CREATE TABLE tb_canteen_type (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 类型ID
    name NVARCHAR(50) NOT NULL UNIQUE,         -- 类型名称
    icon NVARCHAR(255) NULL,                   -- 图标URL
    sort INT NOT NULL DEFAULT 0                -- 排序值
);
GO

-- 2.3 tb_canteen - 食堂表
CREATE TABLE tb_canteen (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 食堂ID
    name NVARCHAR(100) NOT NULL,               -- 食堂名称
    campus_id BIGINT NOT NULL,                   -- 所属校区ID
    type_id BIGINT NOT NULL,                     -- 食堂类型ID
    address NVARCHAR(255) NOT NULL,            -- 详细位置
    floor NVARCHAR(20) NULL,                   -- 楼层信息
    open_hours NVARCHAR(255) NULL,             -- 营业时间
    avg_price INT NULL,                        -- 人均价格
    introduction NVARCHAR(500) NULL,           -- 食堂介绍
    score FLOAT NOT NULL DEFAULT 0,              -- 综合评分
    taste_score FLOAT NOT NULL DEFAULT 0,        -- 口味评分
    environment_score FLOAT NOT NULL DEFAULT 0,  -- 环境评分
    service_score FLOAT NOT NULL DEFAULT 0,      -- 服务评分
    liked INT NOT NULL DEFAULT 0,                -- 点赞数
    comments INT NOT NULL DEFAULT 0,             -- 评论数
    open_status TINYINT NOT NULL DEFAULT 1,      -- 营业状态 (0=休息, 1=营业)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_canteen_campus FOREIGN KEY (campus_id) REFERENCES tb_campus(id),
    CONSTRAINT FK_canteen_type FOREIGN KEY (type_id) REFERENCES tb_canteen_type(id)
);
GO

-- 2.4 tb_stall_type - 窗口类型表
CREATE TABLE tb_stall_type (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 类型ID
    name NVARCHAR(50) NOT NULL UNIQUE,         -- 类型名称
    icon NVARCHAR(255) NULL,                   -- 图标URL
    sort INT NOT NULL DEFAULT 0                -- 排序值
);
GO

-- 2.5 tb_stall - 窗口表
CREATE TABLE tb_stall (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 窗口ID
    name NVARCHAR(100) NOT NULL,               -- 窗口名称
    canteen_id BIGINT NOT NULL,                  -- 所属食堂ID
    type_id BIGINT NOT NULL,                     -- 窗口类型ID
    location NVARCHAR(50) NULL,                -- 窗口位置编号
    introduction NVARCHAR(500) NULL,           -- 窗口介绍
    open_hours NVARCHAR(255) NULL,             -- 营业时间
    score FLOAT NOT NULL DEFAULT 0,              -- 综合评分
    taste_score FLOAT NOT NULL DEFAULT 0,        -- 口味评分
    price_score FLOAT NOT NULL DEFAULT 0,        -- 性价比评分
    comments INT NOT NULL DEFAULT 0,             -- 评论数
    open_status TINYINT NOT NULL DEFAULT 1,      -- 营业状态 (0=休息, 1=营业)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_stall_canteen FOREIGN KEY (canteen_id) REFERENCES tb_canteen(id) ON DELETE CASCADE,
    CONSTRAINT FK_stall_type FOREIGN KEY (type_id) REFERENCES tb_stall_type(id)
);
GO

-- 2.6 tb_dish - 菜品表
CREATE TABLE tb_dish (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 菜品ID
    name NVARCHAR(100) NOT NULL,               -- 菜品名称
    stall_id BIGINT NOT NULL,                    -- 所属窗口ID
    category NVARCHAR(50) NULL,                -- 菜品分类
    price DECIMAL(10, 2) NOT NULL,             -- 价格
    description NVARCHAR(500) NULL,            -- 菜品描述
    score FLOAT NOT NULL DEFAULT 0,              -- 评分
    comments INT NOT NULL DEFAULT 0,             -- 评论提及次数
    status TINYINT NOT NULL DEFAULT 1,           -- 状态 (0=下架, 1=上架)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_dish_stall FOREIGN KEY (stall_id) REFERENCES tb_stall(id) ON DELETE CASCADE
);
GO
-- 3.1 tb_blog - 博客/笔记表
CREATE TABLE tb_blog (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 博客ID
    user_id BIGINT NOT NULL,                     -- 作者ID
    title NVARCHAR(200) NOT NULL,              -- 标题
    content NVARCHAR(2000) NOT NULL,           -- 正文内容
    liked INT NOT NULL DEFAULT 0,                -- 点赞数
    comments INT NOT NULL DEFAULT 0,             -- 评论数
    status TINYINT NOT NULL DEFAULT 0,           -- 状态 (0=正常, 1=隐藏, 2=管理员屏蔽)
    is_top TINYINT NOT NULL DEFAULT 0,           -- 是否置顶 (0=否, 1=是)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_blog_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO

-- 3.2 tb_review - 评价表
CREATE TABLE tb_review (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 评价ID
    user_id BIGINT NOT NULL,                     -- 用户ID
    content NVARCHAR(1024) NOT NULL,           -- 评价内容
    target_id BIGINT NOT NULL,                   -- 被评价对象ID
    target_type TINYINT NOT NULL,                -- 被评价对象类型 (1=食堂, 2=窗口, 3=菜品)
    overall_score INT NOT NULL CHECK (overall_score BETWEEN 1 AND 5), -- 总体评分
    taste_score INT NULL CHECK (taste_score BETWEEN 1 AND 5), -- 口味评分
    environment_score INT NULL CHECK (environment_score BETWEEN 1 AND 5), -- 环境评分
    service_score INT NULL CHECK (service_score BETWEEN 1 AND 5), -- 服务评分
    price_score INT NULL CHECK (price_score BETWEEN 1 AND 5), -- 性价比评分
    liked INT NOT NULL DEFAULT 0,                -- 点赞数
    status TINYINT NOT NULL DEFAULT 0,           -- 状态 (0=待审核, 1=已通过, 2=已拒绝)
    auditor_id BIGINT NULL,                      -- 审核管理员ID
    audit_time DATETIME2 NULL,                   -- 审核时间
    audit_note NVARCHAR(255) NULL,               -- 审核备注
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_review_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT FK_review_auditor FOREIGN KEY (auditor_id) REFERENCES tb_admin(id)
);
GO

-- 3.3 tb_comment - 评论表
CREATE TABLE tb_comment (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 评论ID
    user_id BIGINT NOT NULL,                     -- 用户ID
    target_id BIGINT NOT NULL,                   -- 被评论对象ID (博客或评价)
    target_type TINYINT NOT NULL,                -- 被评论对象类型 (1=博客, 2=评价)
    parent_id BIGINT NULL,                       -- 父评论ID (用于楼中楼回复)
    content NVARCHAR(255) NOT NULL,            -- 评论内容
    liked INT NOT NULL DEFAULT 0,                -- 点赞数
    status TINYINT NOT NULL DEFAULT 0,           -- 状态 (0=待审核, 1=已通过, 2=已拒绝)
    auditor_id BIGINT NULL,                      -- 审核管理员ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_comment_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT FK_comment_parent FOREIGN KEY (parent_id) REFERENCES tb_comment(id),
    CONSTRAINT FK_comment_auditor FOREIGN KEY (auditor_id) REFERENCES tb_admin(id)
);
GO

-- 3.4 tb_like - 点赞表
CREATE TABLE tb_like (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 点赞ID
    user_id BIGINT NOT NULL,                     -- 用户ID
    liked_id BIGINT NOT NULL,                    -- 被点赞对象ID
    type TINYINT NOT NULL,                       -- 点赞对象类型 (1=博客, 2=评价, 3=评论)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_like UNIQUE (user_id, liked_id, type),
    CONSTRAINT FK_like_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO

-- 3.5 tb_favorite - 收藏表
CREATE TABLE tb_favorite (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 收藏ID
    user_id BIGINT NOT NULL,                     -- 用户ID
    favorite_id BIGINT NOT NULL,                 -- 被收藏对象ID
    type TINYINT NOT NULL,                       -- 收藏对象类型 (1=博客, 2=食堂, 3=窗口, 4=菜品)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_favorite UNIQUE (user_id, favorite_id, type),
    CONSTRAINT FK_favorite_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO

-- 3.6 tb_search_history - 搜索历史表
CREATE TABLE tb_search_history (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 历史ID
    user_id BIGINT NOT NULL,                     -- 用户ID
    keyword NVARCHAR(100) NOT NULL,              -- 关键词
    create_time DATETIME2 NOT NULL DEFAULT GETDATE() -- 搜索时间
);
GO

-- 3.7 tb_report - 举报表
CREATE TABLE tb_report (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 举报ID
    user_id BIGINT NOT NULL,                     -- 举报用户ID
    target_id BIGINT NOT NULL,                   -- 举报目标ID
    type TINYINT NOT NULL,                       -- 举报类型 (0=博客, 1=评价, 2=评论, 3=用户)
    reason NVARCHAR(255) NOT NULL,              -- 举报原因
    description NVARCHAR(500) NULL,            -- 举报描述
    status TINYINT NOT NULL DEFAULT 0,           -- 状态 (0=未处理, 1=已处理, 2=已驳回)
    handle_admin_id BIGINT NULL,                 -- 处理管理员ID
    handle_note NVARCHAR(255) NULL,              -- 处理备注
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_report_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT FK_report_admin FOREIGN KEY (handle_admin_id) REFERENCES tb_admin(id)
);
GO
-- 4.1 tb_credit_record - 积分记录表
CREATE TABLE tb_credit_record (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 记录ID
    user_id BIGINT NOT NULL,                     -- 用户ID
    type NVARCHAR(50) NOT NULL,                -- 积分类型 (如: '签到', '评论', '兑换')
    credits INT NOT NULL,                        -- 积分变动值 (正或负)
    description NVARCHAR(255) NULL,            -- 积分描述
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_creditrecord_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO

-- 4.2 tb_sign - 签到表
CREATE TABLE tb_sign (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 签到ID
    user_id BIGINT NOT NULL,                     -- 用户ID
    date DATE NOT NULL,                        -- 签到日期
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_sign UNIQUE (user_id, date)
);
GO

-- 4.3 tb_voucher - 优惠券模板表
CREATE TABLE tb_voucher (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 优惠券ID
    title NVARCHAR(100) NOT NULL,              -- 优惠券标题
    description NVARCHAR(255) NULL,            -- 描述
    type TINYINT NOT NULL DEFAULT 0,             -- 类型 (0=满减券, 1=折扣券)
    price DECIMAL(10, 2) NULL,                 -- 优惠金额
    discount DECIMAL(5, 2) NULL,               -- 折扣率
    min_amount DECIMAL(10, 2) NOT NULL DEFAULT 0, -- 最低消费金额
    canteen_id BIGINT NULL,                      -- 限定食堂ID
    start_time DATETIME2 NOT NULL,               -- 生效时间
    end_time DATETIME2 NOT NULL,                 -- 过期时间
    stock INT NOT NULL DEFAULT 0,                -- 库存
    required_credits INT NOT NULL DEFAULT 0,     -- 兑换所需积分
    status TINYINT NOT NULL DEFAULT 0,           -- 状态 (0=未开始, 1=进行中, 2=已结束)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 4.4 tb_user_voucher - 用户优惠券表
CREATE TABLE tb_user_voucher (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 用户券ID
    user_id BIGINT NOT NULL,                     -- 用户ID
    voucher_id BIGINT NOT NULL,                  -- 优惠券ID
    status TINYINT NOT NULL DEFAULT 0,           -- 状态 (0=未使用, 1=已使用, 2=已过期)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- 领取时间
    use_time DATETIME2 NULL,                     -- 使用时间
    CONSTRAINT FK_uservoucher_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT FK_uservoucher_voucher FOREIGN KEY (voucher_id) REFERENCES tb_voucher(id)
);
GO
-- 5.1 tb_banner - 轮播图表
CREATE TABLE tb_banner (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 轮播图ID
    title NVARCHAR(100) NOT NULL,              -- 标题
    image_url NVARCHAR(255) NOT NULL,          -- 图片URL
    link_url NVARCHAR(255) NULL,               -- 跳转链接
    sort INT NOT NULL DEFAULT 0,                 -- 排序值
    status TINYINT NOT NULL DEFAULT 0,           -- 状态 (0=下线, 1=上线)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 5.2 tb_notice - 系统公告表
CREATE TABLE tb_notice (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 公告ID
    admin_id BIGINT NOT NULL,                    -- 发布管理员ID
    title NVARCHAR(100) NOT NULL,              -- 公告标题
    content NVARCHAR(MAX) NOT NULL,            -- 公告内容
    type TINYINT NOT NULL DEFAULT 0,             -- 公告类型 (0=普通, 1=重要)
    status TINYINT NOT NULL DEFAULT 0,           -- 状态 (0=草稿, 1=已发布)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_notice_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id)
);
GO

-- 5.3 tb_log - 系统日志表
CREATE TABLE tb_log (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 日志ID
    admin_id BIGINT NULL,                        -- 操作的管理员ID
    operation NVARCHAR(100) NOT NULL,          -- 操作描述
    method NVARCHAR(255) NULL,                 -- 请求方法
    params NVARCHAR(1000) NULL,                -- 请求参数
    ip VARCHAR(50) NULL,                         -- IP地址
    create_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 5.4 tb_file - 文件表 (公共)
CREATE TABLE tb_file (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 文件ID
    uploader_id BIGINT NOT NULL,                 -- 上传者ID
    uploader_type TINYINT NOT NULL,              -- 上传者类型 (1=用户, 2=管理员)
    target_id BIGINT NULL,                       -- 关联对象ID
    target_type TINYINT NULL,                    -- 关联对象类型
    name NVARCHAR(255) NOT NULL,               -- 系统生成的文件名
    original_name NVARCHAR(255) NOT NULL,      -- 原始文件名
    url NVARCHAR(255) NOT NULL,                -- 文件URL
    type VARCHAR(50) NOT NULL,                   -- 文件MIME类型
    size INT NOT NULL,                           -- 文件大小(字节)
    status TINYINT NOT NULL DEFAULT 0,           -- 状态 (0=正常, 1=已删除)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO
-- 6.1 tb_ranking - 排行榜表
CREATE TABLE tb_ranking (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 排行ID
    type TINYINT NOT NULL,                       -- 排行类型 (0=菜品, 1=食堂, 2=窗口, 3=用户积分)
    target_id BIGINT NOT NULL,                   -- 目标ID
    score FLOAT NOT NULL,                        -- 评分/数值
    rank INT NOT NULL,                           -- 排名
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_ranking UNIQUE (type, target_id)
);
GO

-- 6.2 tb_statistics - 统计数据表
CREATE TABLE tb_statistics (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- 统计ID
    type TINYINT NOT NULL,                       -- 统计类型 (0=用户活跃度, 1=评分分布)
    target_id BIGINT NULL,                       -- 目标ID (如食堂ID)
    period VARCHAR(20) NOT NULL,               -- 统计周期 (day/week/month/year)
    date DATE NOT NULL,                        -- 统计日期
    data NVARCHAR(MAX) NOT NULL,               -- 统计数据(JSON字符串)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_statistics UNIQUE (type, target_id, period, date)
);
GO