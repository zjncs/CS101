/*
 TjuFood - 数据库完整模拟数据插入脚本
 版本: v1.6 (修正版)
 描述: 此脚本用于为优化后的TjuFood数据库(v3修正版)的所有核心表填充一套完整、
       相互关联的模拟数据，以便于开发、测试和功能演示。
       v1.6更新：
       1. 将所有密码更新为新的BCrypt加密字符串。
       2. 移除了所有SET IDENTITY_INSERT语句，以匹配新的、不使用数据库自增的表结构。
*/

-- =============================================================================
-- 可选：数据清理模块
-- 描述: 如果您需要在已有数据的数据库上重新运行此脚本，请取消下面的注释。
-- 警告: 这将会删除所有相关表中的数据！
-- =============================================================================
/*
PRINT '开始清理已有数据...';
-- 注意：删除顺序与建表顺序相反，从子表开始删
DELETE FROM tb_user_feedback;
DELETE FROM tb_system_config;
DELETE FROM tb_help_doc;
DELETE FROM tb_sensitive_word;
DELETE FROM tb_file;
DELETE FROM tb_log;
DELETE FROM tb_notice;
DELETE FROM tb_banner;
DELETE FROM tb_incentive_rule;
DELETE FROM tb_user_voucher;
DELETE FROM tb_voucher;
DELETE FROM tb_sign;
DELETE FROM tb_credit_record;
DELETE FROM tb_notification;
DELETE FROM tb_report;
DELETE FROM tb_search_history;
DELETE FROM tb_favorite;
DELETE FROM tb_like;
DELETE FROM tb_comment;
DELETE FROM tb_stall_review;
DELETE FROM tb_blog;
DELETE FROM tb_dish;
DELETE FROM tb_stall;
DELETE FROM tb_stall_type;
DELETE FROM tb_canteen;
DELETE FROM tb_canteen_type;
DELETE FROM tb_campus;
DELETE FROM tb_role_permission;
DELETE FROM tb_admin_role;
DELETE FROM tb_permission;
DELETE FROM tb_role;
DELETE FROM tb_admin;
DELETE FROM tb_user;
PRINT '数据清理完成。';
*/


-- =============================================================================
-- 开始插入数据
-- =============================================================================

PRINT '开始插入模拟数据...';

BEGIN TRANSACTION;
BEGIN TRY

-- 声明将在整个脚本中使用的通用变量
-- [核心修改] 更新为新的BCrypt加密密码
DECLARE @EncryptedPassword NVARCHAR(100) = '$2a$10$0ObG.exCgXhMzv/1XlDOoeVv0zYVSHrA17vqIgjx61AJiTibqZ10W';


-- =============================================================================
-- 模块 1: 用户与权限 (User & Permissions)
-- =============================================================================

PRINT '正在插入: 模块 1 - 用户与权限...';

-- 1.1: 插入角色 (tb_role)
-- [核心修改] 移除 SET IDENTITY_INSERT
INSERT INTO tb_role (id, name, description, status) VALUES
(1, '系统管理员', '拥有系统所有权限的超级管理员', 1),
(2, '内容管理员', '负责管理食堂、窗口、菜品等核心基础数据', 1),
(3, '社区管理员', '负责审核博客、处理举报、管理用户和敏感词', 1),
(4, '运营管理员', '负责平台运营活动，如轮播图、公告、优惠券等', 1);

-- 1.2: 插入管理员 (tb_admin)
INSERT INTO tb_admin (id, username, password, name, status) VALUES
(1, 'sysadmin', @EncryptedPassword, '系统管理员-张主管', 0),
(2, 'contentadmin', @EncryptedPassword, '内容管理员-李编辑', 0),
(3, 'communityadmin', @EncryptedPassword, '社区管理员-王审核', 0),
(4, 'opsadmin', @EncryptedPassword, '运营管理员-赵运营', 0);

-- 1.3: 关联管理员与角色 (tb_admin_role)
-- 注意: tb_admin_role的ID也需要手动指定
INSERT INTO tb_admin_role (id, admin_id, role_id) VALUES
(1, 1, 1), -- sysadmin       -> 系统管理员
(2, 2, 2), -- contentadmin   -> 内容管理员
(3, 3, 3), -- communityadmin -> 社区管理员
(4, 4, 4); -- opsadmin       -> 运营管理员

-- 1.4: 插入权限定义 (tb_permission)
INSERT INTO tb_permission (id, name, description) VALUES
(1, 'content:campus:read', '查看校区列表'),
(2, 'content:campus:create', '新增校区'),
(3, 'content:campus:update', '修改校区'),
(4, 'content:campus:delete', '删除校区'),
(5, 'content:canteen:read', '查看食堂列表与类型'),
(6, 'content:canteen:create', '新增食堂与类型'),
(7, 'content:canteen:update', '修改食堂与类型'),
(8, 'content:canteen:delete', '删除食堂与类型'),
(9, 'content:stall:read', '查看窗口列表与类型'),
(10, 'content:stall:create', '新增窗口与类型'),
(11, 'content:stall:update', '修改窗口与类型'),
(12, 'content:stall:delete', '删除窗口与类型'),
(13, 'content:dish:read', '查看菜品列表'),
(14, 'content:dish:create', '新增菜品'),
(15, 'content:dish:update', '修改菜品'),
(16, 'content:dish:delete', '删除菜品'),
(17, 'community:blog:audit', '审核博客'),
(18, 'community:blog:manage', '管理博客(置顶/加精)'),
(19, 'community:report:manage', '处理举报'),
(20, 'community:word:manage', '管理敏感词'),
(21, 'community:user:manage', '管理用户(禁用/解禁)'),
(22, 'operations:banner:manage', '管理轮播图'),
(23, 'operations:notice:manage', '管理系统公告'),
(24, 'operations:voucher:manage', '管理优惠券'),
(25, 'operations:incentive:manage', '管理激励规则'),
(26, 'operations:feedback:manage', '处理用户反馈'),
(27, 'platform:admin:manage', '管理管理员账号'),
(28, 'platform:role:manage', '管理角色与权限'),
(29, 'platform:config:manage', '管理系统参数'),
(30, 'platform:log:read', '查看系统日志'),
(31, 'toolbox:dashboard:view', '查看仪表盘'),
(32, 'toolbox:stats:view', '查看数据统计'),
(33, 'toolbox:task:manage', '管理定时任务'),
(34, 'toolbox:help:manage', '管理帮助文档'),
(35, 'toolbox:export:log', '导出操作日志'),
(36, 'toolbox:export:db', '导出数据库备份');

-- 1.5: 关联角色与权限 (tb_role_permission)
-- 注意: tb_role_permission的ID也需要手动指定
INSERT INTO tb_role_permission (id, role_id, permission_id) VALUES
-- 内容管理员 (role_id = 2)
(1, 2, 1), (2, 2, 2), (3, 2, 3), (4, 2, 4), (5, 2, 5), (6, 2, 6), (7, 2, 7), (8, 2, 8), (9, 2, 9), (10, 2, 10), (11, 2, 11), (12, 2, 12), (13, 2, 13), (14, 2, 14), (15, 2, 15), (16, 2, 16), (17, 2, 31),
-- 社区管理员 (role_id = 3)
(18, 3, 17), (19, 3, 18), (20, 3, 19), (21, 3, 20), (22, 3, 21), (23, 3, 31),
-- 运营管理员 (role_id = 4)
(24, 4, 22), (25, 4, 23), (26, 4, 24), (27, 4, 25), (28, 4, 26), (29, 4, 30), (30, 4, 31), (31, 4, 35);

-- 系统管理员 (role_id = 1) 拥有所有权限 (1-36)
-- 使用循环插入，ID从32开始
DECLARE @i INT = 1;
DECLARE @role_perm_id INT = 32;
WHILE @i <= 36
BEGIN
    -- 避免重复插入已存在的关联
    IF NOT EXISTS (SELECT 1 FROM tb_role_permission WHERE role_id = 1 AND permission_id = @i)
    BEGIN
        INSERT INTO tb_role_permission (id, role_id, permission_id) VALUES (@role_perm_id, 1, @i);
        SET @role_perm_id = @role_perm_id + 1;
    END
    SET @i = @i + 1;
END;

-- 1.6: 插入普通用户 (tb_user)
INSERT INTO tb_user (id, password, email, phone, nickname, bio, campus, credits, level) VALUES
(1, @EncryptedPassword, 'user1@example.com', '13800138001', '张三', '一名热爱美食的学生。', '卫津路校区', 100, 2),
(2, @EncryptedPassword, 'user2@example.com', '13900139002', '李四', '干饭人，干饭魂！', '北洋园校区', 50, 1);


-- =============================================================================
-- 模块 2: 核心基础数据 (Core Data)
-- =============================================================================

PRINT '正在插入: 模块 2 - 核心基础数据...';

INSERT INTO tb_campus (id, name, address) VALUES
(1, '卫津路校区', '天津市南开区卫津路92号'),
(2, '北洋园校区', '天津市津南区海河教育园区雅观路135号');

INSERT INTO tb_canteen_type (id, name, icon, sort) VALUES
(1, '学生食堂', 'canteen-type-student.png', 1),
(2, '教工食堂', 'canteen-type-faculty.png', 2),
(3, '风味餐厅', 'canteen-type-special.png', 3);

INSERT INTO tb_canteen (id, name, campus_id, type_id, address, floor, open_hours) VALUES
(1, '学一食堂', 1, 1, '卫津路校区内，青年湖旁', '共三层', '06:30-09:00, 11:00-13:00, 17:00-19:00'),
(2, '学五食堂', 1, 1, '卫津路校区内，敬业湖附近', '共两层', '06:30-09:00, 11:00-13:00, 17:00-19:00'),
(3, '留园餐厅', 1, 3, '留学生宿舍区', '共一层', '11:00-21:00'),
(4, '学三食堂（格园）', 2, 1, '北洋园校区格园宿舍区', '共三层', '06:30-09:30, 10:30-13:30, 16:30-19:30'),
(5, '学四食堂（诚园）', 2, 1, '北洋园校区诚园宿舍区', '共两层', '06:30-09:30, 10:30-13:30, 16:30-19:30');

INSERT INTO tb_stall_type (id, name, icon, sort) VALUES
(1, '面食点心', 'stall-type-noodles.png', 1),
(2, '特色盖饭', 'stall-type-rice.png', 2),
(3, '麻辣香锅', 'stall-type-malaxiangguo.png', 3),
(4, '精品小炒', 'stall-type-stirfry.png', 4),
(5, '烧烤铁板', 'stall-type-bbq.png', 5),
(6, '奶茶饮品', 'stall-type-drinks.png', 6);

INSERT INTO tb_stall (id, name, canteen_id, type_id, location, introduction) VALUES
(1, '老王牛肉面', 1, 1, '一层A区01号', '传承三十年的经典牛肉面，汤浓面劲。'),
(2, '张姐烤肉饭', 1, 2, '一层B区05号', '秘制酱料，肉香四溢。'),
(3, '川香麻辣香锅', 1, 3, '二层C区12号', '自选食材，麻辣过瘾。'),
(4, '津味小炒', 2, 4, '一层03号', '地道天津味，家常菜首选。'),
(5, '滋滋铁板烧', 4, 5, '二层美食广场', '现场制作，新鲜美味。'),
(6, 'CoCo都可', 4, 6, '一层入口处', '人气奶茶，全糖去冰。');

INSERT INTO tb_dish (id, name, stall_id, category, price, description) VALUES
(1, '红烧牛肉面', 1, '招牌面', 15.00, '大块牛肉，鲜美汤底。'),
(2, '酸菜牛肉面', 1, '特色面', 16.00, '酸爽开胃，回味无穷。'),
(3, '烤肉饭套餐', 2, '主食套餐', 18.00, '包含烤肉、米饭、小菜和汤。'),
(4, '双拼烤肉饭', 2, '主食套餐', 22.00, '鸡肉和猪肉的双重满足。'),
(5, '素什锦香锅', 3, '自选香锅', 12.50, '基础价格，按重量计费。'),
(6, '精品肥牛香锅', 3, '推荐香锅', 28.00, '包含肥牛、午餐肉、多种蔬菜。'),
(7, '鱼香肉丝', 4, '经典小炒', 12.00, '经典川菜，酸甜可口。'),
(8, '宫保鸡丁', 4, '经典小炒', 14.00, '鸡丁滑嫩，花生香脆。'),
(9, '黑椒牛柳铁板饭', 5, '铁板饭', 20.00, '黑胡椒酱汁浓郁。'),
(10, '奶茶三兄弟', 6, '经典奶茶', 13.00, '包含布丁、仙草、珍珠。');


-- =============================================================================
-- 模块 3 & 4: 用户激励, 内容与互动
-- =============================================================================

PRINT '正在插入: 模块 3 & 4 - 用户激励, 内容与互动...';

INSERT INTO tb_incentive_rule (id, action_type, credits, daily_limit, description) VALUES
(1, 'DAILY_SIGN_IN', 5, 1, '每日签到获取积分'),
(2, 'POST_BLOG', 20, 2, '发表一篇博客获取积分'),
(3, 'ADD_REVIEW', 10, 5, '提交一条窗口评价获取积分'),
(4, 'ADD_COMMENT', 2, 10, '发表一条评论获取积分');

INSERT INTO tb_voucher (id, title, description, type, price, min_amount, stock, required_credits, start_time, end_time, status) VALUES
(1, '新生季5元满减券', '全场通用，满20元可用', 0, 5.00, 20.00, 1000, 50, GETDATE(), DATEADD(month, 1, GETDATE()), 1);

INSERT INTO tb_blog (id, user_id, title, content, status) VALUES
(1, 1, '学一食堂探店报告！', '今天和室友去了学一食堂，简直是美食天堂！强烈推荐二楼的麻辣香锅，味道太正宗了！牛肉面也很好吃，下次再来尝尝别的。![图片]', 1),
(2, 2, '北洋园最好喝的奶茶？', '诚园的CoCo永远在排队，但是味道确实不错。奶茶三兄弟yyds！大家还有没有其他推荐的奶茶店呀？', 1);

INSERT INTO tb_credit_record(id, user_id, action_type, credits, description) VALUES
(1, 1, 'POST_BLOG', 20, '发表博客《学一食堂探店报告！》'),
(2, 2, 'POST_BLOG', 20, '发表博客《北洋园最好喝的奶茶？》');

INSERT INTO tb_stall_review (id, user_id, stall_id, content, cost_per_person, overall_score, taste_score, environment_score, service_score, price_score) VALUES
(1, 1, 3, '味道超赞，就是要排队好久。食材很新鲜，可以自己选，价格也还行。', 25.00, 5, 5, 4, 4, 4),
(2, 2, 6, '全糖真的太甜了，下次一定半糖。不过料给的很足，好评！', 13.00, 4, 4, 4, 5, 4);

INSERT INTO tb_credit_record(id, user_id, action_type, credits, description) VALUES
(3, 1, 'ADD_REVIEW', 10, '评价窗口“川香麻辣香锅”'),
(4, 2, 'ADD_REVIEW', 10, '评价窗口“CoCo都可”');

INSERT INTO tb_comment (id, user_id, target_id, target_type, content) VALUES
(1, 2, 1, 1, '哇，看起来好好吃，下次我也去学一尝尝！');
INSERT INTO tb_comment (id, user_id, target_id, target_type, parent_id, content) VALUES
(2, 1, 1, 2, 1, '快去快去，绝对不亏！');

INSERT INTO tb_credit_record(id, user_id, action_type, credits, description) VALUES
(5, 2, 'ADD_COMMENT', 2, '评论博客《学一食堂探店报告！》'),
(6, 1, 'ADD_COMMENT', 2, '回复评论');

INSERT INTO tb_like (id, user_id, liked_id, type) VALUES
(1, 1, 10, 3), -- 用户1点赞菜品'奶茶三兄弟'
(2, 2, 1, 1),  -- 用户2点赞博客1
(3, 1, 2, 2);  -- 用户1点赞评论2

UPDATE tb_dish SET liked = liked + 1 WHERE id = 10;
UPDATE tb_blog SET liked = liked + 1, comments = comments + 2 WHERE id = 1;
UPDATE tb_comment SET liked = liked + 1 WHERE id = 2; 

INSERT INTO tb_favorite (id, user_id, favorite_id, type) VALUES
(1, 1, 6, 2), -- 用户1收藏窗口'CoCo都可'
(2, 2, 1, 1); -- 用户2收藏博客1

INSERT INTO tb_notification (id, recipient_id, sender_id, type, target_id, target_content) VALUES
(1, 1, 2, 1, 1, '哇，看起来好好吃，下次我也去学一尝尝！'), -- user2评论了user1的博客
(2, 2, 1, 2, 2, '快去快去，绝对不亏！'),                 -- user1回复了user2的评论
(3, 1, 2, 3, 1, '学一食堂探店报告！');                      -- user2点赞了user1的博客


-- =============================================================================
-- 模块 5: 系统管理与展示 (System & Display)
-- =============================================================================

PRINT '正在插入: 模块 5 - 系统管理与展示...';

INSERT INTO tb_banner (id, title, image_url, link_url, sort, status, start_time, end_time) VALUES
(1, '毕业季餐饮优惠', 'banner1.jpg', '/promotions/graduation', 1, 1, GETDATE(), DATEADD(month, 1, GETDATE())),
(2, '迎新周美食推荐', 'banner2.jpg', '/topics/welcome-new', 2, 1, GETDATE(), DATEADD(month, 1, GETDATE()));

INSERT INTO tb_notice (id, admin_id, title, content, type, status, publish_time) VALUES
(1, 1, '关于夏季食堂开放时间调整的通知', '各位师生：为应对夏季作息，自7月1日起，各食堂晚餐供应时间延长至晚8点，请相互转告。', 1, 1, GETDATE());

INSERT INTO tb_sensitive_word (id, word, category, level, admin_id) VALUES
(1, '广告', '广告', 1, 3),
(2, '加V', '广告', 1, 3),
(3, '傻子', '人身攻击', 2, 3);

INSERT INTO tb_system_config (id, config_key, config_name, config_value, remark) VALUES
(1, 'site_name', '网站名称', 'TjuFood 天大美食', '显示在浏览器标签页和页面头部'),
(2, 'default_user_avatar', '用户默认头像URL', 'default_avatar.png', '新用户注册时使用的默认头像');


    COMMIT TRANSACTION;
END TRY
BEGIN CATCH
    ROLLBACK TRANSACTION;
    PRINT '脚本执行过程中发生错误，所有操作已回滚。';
    THROW;
END CATCH;


PRINT '所有模拟数据插入完成！';
