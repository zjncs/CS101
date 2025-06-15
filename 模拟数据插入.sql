/*
 TjuFood - ���ݿ�����ģ�����ݲ���ű�
 �汾: v1.6 (������)
 ����: �˽ű�����Ϊ�Ż����TjuFood���ݿ�(v3������)�����к��ı����һ��������
       �໥������ģ�����ݣ��Ա��ڿ��������Ժ͹�����ʾ��
       v1.6���£�
       1. �������������Ϊ�µ�BCrypt�����ַ�����
       2. �Ƴ�������SET IDENTITY_INSERT��䣬��ƥ���µġ���ʹ�����ݿ������ı�ṹ��
*/

-- =============================================================================
-- ��ѡ����������ģ��
-- ����: �������Ҫ���������ݵ����ݿ����������д˽ű�����ȡ�������ע�͡�
-- ����: �⽫��ɾ��������ر��е����ݣ�
-- =============================================================================
/*
PRINT '��ʼ������������...';
-- ע�⣺ɾ��˳���뽨��˳���෴�����ӱ�ʼɾ
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
PRINT '����������ɡ�';
*/


-- =============================================================================
-- ��ʼ��������
-- =============================================================================

PRINT '��ʼ����ģ������...';

BEGIN TRANSACTION;
BEGIN TRY

-- �������������ű���ʹ�õ�ͨ�ñ���
-- [�����޸�] ����Ϊ�µ�BCrypt��������
DECLARE @EncryptedPassword NVARCHAR(100) = '$2a$10$0ObG.exCgXhMzv/1XlDOoeVv0zYVSHrA17vqIgjx61AJiTibqZ10W';


-- =============================================================================
-- ģ�� 1: �û���Ȩ�� (User & Permissions)
-- =============================================================================

PRINT '���ڲ���: ģ�� 1 - �û���Ȩ��...';

-- 1.1: �����ɫ (tb_role)
-- [�����޸�] �Ƴ� SET IDENTITY_INSERT
INSERT INTO tb_role (id, name, description, status) VALUES
(1, 'ϵͳ����Ա', 'ӵ��ϵͳ����Ȩ�޵ĳ�������Ա', 1),
(2, '���ݹ���Ա', '�������ʳ�á����ڡ���Ʒ�Ⱥ��Ļ�������', 1),
(3, '��������Ա', '������˲��͡�����ٱ��������û������д�', 1),
(4, '��Ӫ����Ա', '����ƽ̨��Ӫ������ֲ�ͼ�����桢�Ż�ȯ��', 1);

-- 1.2: �������Ա (tb_admin)
INSERT INTO tb_admin (id, username, password, name, status) VALUES
(1, 'sysadmin', @EncryptedPassword, 'ϵͳ����Ա-������', 0),
(2, 'contentadmin', @EncryptedPassword, '���ݹ���Ա-��༭', 0),
(3, 'communityadmin', @EncryptedPassword, '��������Ա-�����', 0),
(4, 'opsadmin', @EncryptedPassword, '��Ӫ����Ա-����Ӫ', 0);

-- 1.3: ��������Ա���ɫ (tb_admin_role)
-- ע��: tb_admin_role��IDҲ��Ҫ�ֶ�ָ��
INSERT INTO tb_admin_role (id, admin_id, role_id) VALUES
(1, 1, 1), -- sysadmin       -> ϵͳ����Ա
(2, 2, 2), -- contentadmin   -> ���ݹ���Ա
(3, 3, 3), -- communityadmin -> ��������Ա
(4, 4, 4); -- opsadmin       -> ��Ӫ����Ա

-- 1.4: ����Ȩ�޶��� (tb_permission)
INSERT INTO tb_permission (id, name, description) VALUES
(1, 'content:campus:read', '�鿴У���б�'),
(2, 'content:campus:create', '����У��'),
(3, 'content:campus:update', '�޸�У��'),
(4, 'content:campus:delete', 'ɾ��У��'),
(5, 'content:canteen:read', '�鿴ʳ���б�������'),
(6, 'content:canteen:create', '����ʳ��������'),
(7, 'content:canteen:update', '�޸�ʳ��������'),
(8, 'content:canteen:delete', 'ɾ��ʳ��������'),
(9, 'content:stall:read', '�鿴�����б�������'),
(10, 'content:stall:create', '��������������'),
(11, 'content:stall:update', '�޸Ĵ���������'),
(12, 'content:stall:delete', 'ɾ������������'),
(13, 'content:dish:read', '�鿴��Ʒ�б�'),
(14, 'content:dish:create', '������Ʒ'),
(15, 'content:dish:update', '�޸Ĳ�Ʒ'),
(16, 'content:dish:delete', 'ɾ����Ʒ'),
(17, 'community:blog:audit', '��˲���'),
(18, 'community:blog:manage', '������(�ö�/�Ӿ�)'),
(19, 'community:report:manage', '����ٱ�'),
(20, 'community:word:manage', '�������д�'),
(21, 'community:user:manage', '�����û�(����/���)'),
(22, 'operations:banner:manage', '�����ֲ�ͼ'),
(23, 'operations:notice:manage', '����ϵͳ����'),
(24, 'operations:voucher:manage', '�����Ż�ȯ'),
(25, 'operations:incentive:manage', '����������'),
(26, 'operations:feedback:manage', '�����û�����'),
(27, 'platform:admin:manage', '�������Ա�˺�'),
(28, 'platform:role:manage', '�����ɫ��Ȩ��'),
(29, 'platform:config:manage', '����ϵͳ����'),
(30, 'platform:log:read', '�鿴ϵͳ��־'),
(31, 'toolbox:dashboard:view', '�鿴�Ǳ���'),
(32, 'toolbox:stats:view', '�鿴����ͳ��'),
(33, 'toolbox:task:manage', '����ʱ����'),
(34, 'toolbox:help:manage', '��������ĵ�'),
(35, 'toolbox:export:log', '����������־'),
(36, 'toolbox:export:db', '�������ݿⱸ��');

-- 1.5: ������ɫ��Ȩ�� (tb_role_permission)
-- ע��: tb_role_permission��IDҲ��Ҫ�ֶ�ָ��
INSERT INTO tb_role_permission (id, role_id, permission_id) VALUES
-- ���ݹ���Ա (role_id = 2)
(1, 2, 1), (2, 2, 2), (3, 2, 3), (4, 2, 4), (5, 2, 5), (6, 2, 6), (7, 2, 7), (8, 2, 8), (9, 2, 9), (10, 2, 10), (11, 2, 11), (12, 2, 12), (13, 2, 13), (14, 2, 14), (15, 2, 15), (16, 2, 16), (17, 2, 31),
-- ��������Ա (role_id = 3)
(18, 3, 17), (19, 3, 18), (20, 3, 19), (21, 3, 20), (22, 3, 21), (23, 3, 31),
-- ��Ӫ����Ա (role_id = 4)
(24, 4, 22), (25, 4, 23), (26, 4, 24), (27, 4, 25), (28, 4, 26), (29, 4, 30), (30, 4, 31), (31, 4, 35);

-- ϵͳ����Ա (role_id = 1) ӵ������Ȩ�� (1-36)
-- ʹ��ѭ�����룬ID��32��ʼ
DECLARE @i INT = 1;
DECLARE @role_perm_id INT = 32;
WHILE @i <= 36
BEGIN
    -- �����ظ������Ѵ��ڵĹ���
    IF NOT EXISTS (SELECT 1 FROM tb_role_permission WHERE role_id = 1 AND permission_id = @i)
    BEGIN
        INSERT INTO tb_role_permission (id, role_id, permission_id) VALUES (@role_perm_id, 1, @i);
        SET @role_perm_id = @role_perm_id + 1;
    END
    SET @i = @i + 1;
END;

-- 1.6: ������ͨ�û� (tb_user)
INSERT INTO tb_user (id, password, email, phone, nickname, bio, campus, credits, level) VALUES
(1, @EncryptedPassword, 'user1@example.com', '13800138001', '����', 'һ���Ȱ���ʳ��ѧ����', '����·У��', 100, 2),
(2, @EncryptedPassword, 'user2@example.com', '13900139002', '����', '�ɷ��ˣ��ɷ��꣡', '����԰У��', 50, 1);


-- =============================================================================
-- ģ�� 2: ���Ļ������� (Core Data)
-- =============================================================================

PRINT '���ڲ���: ģ�� 2 - ���Ļ�������...';

INSERT INTO tb_campus (id, name, address) VALUES
(1, '����·У��', '������Ͽ�������·92��'),
(2, '����԰У��', '����н��������ӽ���԰���Ź�·135��');

INSERT INTO tb_canteen_type (id, name, icon, sort) VALUES
(1, 'ѧ��ʳ��', 'canteen-type-student.png', 1),
(2, '�̹�ʳ��', 'canteen-type-faculty.png', 2),
(3, '��ζ����', 'canteen-type-special.png', 3);

INSERT INTO tb_canteen (id, name, campus_id, type_id, address, floor, open_hours) VALUES
(1, 'ѧһʳ��', 1, 1, '����·У���ڣ��������', '������', '06:30-09:00, 11:00-13:00, 17:00-19:00'),
(2, 'ѧ��ʳ��', 1, 1, '����·У���ڣ���ҵ������', '������', '06:30-09:00, 11:00-13:00, 17:00-19:00'),
(3, '��԰����', 1, 3, '��ѧ��������', '��һ��', '11:00-21:00'),
(4, 'ѧ��ʳ�ã���԰��', 2, 1, '����԰У����԰������', '������', '06:30-09:30, 10:30-13:30, 16:30-19:30'),
(5, 'ѧ��ʳ�ã���԰��', 2, 1, '����԰У����԰������', '������', '06:30-09:30, 10:30-13:30, 16:30-19:30');

INSERT INTO tb_stall_type (id, name, icon, sort) VALUES
(1, '��ʳ����', 'stall-type-noodles.png', 1),
(2, '��ɫ�Ƿ�', 'stall-type-rice.png', 2),
(3, '�������', 'stall-type-malaxiangguo.png', 3),
(4, '��ƷС��', 'stall-type-stirfry.png', 4),
(5, '�տ�����', 'stall-type-bbq.png', 5),
(6, '�̲���Ʒ', 'stall-type-drinks.png', 6);

INSERT INTO tb_stall (id, name, canteen_id, type_id, location, introduction) VALUES
(1, '����ţ����', 1, 1, 'һ��A��01��', '������ʮ��ľ���ţ���棬��Ũ�澢��'),
(2, '�Ž㿾�ⷹ', 1, 2, 'һ��B��05��', '���ƽ��ϣ��������硣'),
(3, '�����������', 1, 3, '����C��12��', '��ѡʳ�ģ�������񫡣'),
(4, '��ζС��', 2, 4, 'һ��03��', '�ص����ζ���ҳ�����ѡ��'),
(5, '����������', 4, 5, '������ʳ�㳡', '�ֳ�������������ζ��'),
(6, 'CoCo����', 4, 6, 'һ����ڴ�', '�����̲裬ȫ��ȥ����');

INSERT INTO tb_dish (id, name, stall_id, category, price, description) VALUES
(1, '����ţ����', 1, '������', 15.00, '���ţ�⣬�������ס�'),
(2, '���ţ����', 1, '��ɫ��', 16.00, '��ˬ��θ����ζ���'),
(3, '���ⷹ�ײ�', 2, '��ʳ�ײ�', 18.00, '�������⡢�׷���С�˺�����'),
(4, '˫ƴ���ⷹ', 2, '��ʳ�ײ�', 22.00, '����������˫�����㡣'),
(5, '��ʲ�����', 3, '��ѡ���', 12.50, '�����۸񣬰������Ʒѡ�'),
(6, '��Ʒ��ţ���', 3, '�Ƽ����', 28.00, '������ţ������⡢�����߲ˡ�'),
(7, '������˿', 4, '����С��', 12.00, '���䴨�ˣ�����ɿڡ�'),
(8, '��������', 4, '����С��', 14.00, '�������ۣ�������ࡣ'),
(9, '�ڽ�ţ�����巹', 5, '���巹', 20.00, '�ں�����֭Ũ����'),
(10, '�̲����ֵ�', 6, '�����̲�', 13.00, '�����������ɲݡ����顣');


-- =============================================================================
-- ģ�� 3 & 4: �û�����, �����뻥��
-- =============================================================================

PRINT '���ڲ���: ģ�� 3 & 4 - �û�����, �����뻥��...';

INSERT INTO tb_incentive_rule (id, action_type, credits, daily_limit, description) VALUES
(1, 'DAILY_SIGN_IN', 5, 1, 'ÿ��ǩ����ȡ����'),
(2, 'POST_BLOG', 20, 2, '����һƪ���ͻ�ȡ����'),
(3, 'ADD_REVIEW', 10, 5, '�ύһ���������ۻ�ȡ����'),
(4, 'ADD_COMMENT', 2, 10, '����һ�����ۻ�ȡ����');

INSERT INTO tb_voucher (id, title, description, type, price, min_amount, stock, required_credits, start_time, end_time, status) VALUES
(1, '������5Ԫ����ȯ', 'ȫ��ͨ�ã���20Ԫ����', 0, 5.00, 20.00, 1000, 50, GETDATE(), DATEADD(month, 1, GETDATE()), 1);

INSERT INTO tb_blog (id, user_id, title, content, status) VALUES
(1, 1, 'ѧһʳ��̽�걨�棡', '���������ȥ��ѧһʳ�ã���ֱ����ʳ���ã�ǿ���Ƽ���¥�����������ζ��̫�����ˣ�ţ����Ҳ�ܺóԣ��´�����������ġ�![ͼƬ]', 1),
(2, 2, '����԰��úȵ��̲裿', '��԰��CoCo��Զ���Ŷӣ�����ζ��ȷʵ�����̲����ֵ�yyds����һ���û�������Ƽ����̲��ѽ��', 1);

INSERT INTO tb_credit_record(id, user_id, action_type, credits, description) VALUES
(1, 1, 'POST_BLOG', 20, '�����͡�ѧһʳ��̽�걨�棡��'),
(2, 2, 'POST_BLOG', 20, '�����͡�����԰��úȵ��̲裿��');

INSERT INTO tb_stall_review (id, user_id, stall_id, content, cost_per_person, overall_score, taste_score, environment_score, service_score, price_score) VALUES
(1, 1, 3, 'ζ�����ޣ�����Ҫ�ŶӺþá�ʳ�ĺ����ʣ������Լ�ѡ���۸�Ҳ���С�', 25.00, 5, 5, 4, 4, 4),
(2, 2, 6, 'ȫ�����̫���ˣ��´�һ�����ǡ������ϸ��ĺ��㣬������', 13.00, 4, 4, 4, 5, 4);

INSERT INTO tb_credit_record(id, user_id, action_type, credits, description) VALUES
(3, 1, 'ADD_REVIEW', 10, '���۴��ڡ��������������'),
(4, 2, 'ADD_REVIEW', 10, '���۴��ڡ�CoCo���ɡ�');

INSERT INTO tb_comment (id, user_id, target_id, target_type, content) VALUES
(1, 2, 1, 1, '�ۣ��������úóԣ��´���Ҳȥѧһ������');
INSERT INTO tb_comment (id, user_id, target_id, target_type, parent_id, content) VALUES
(2, 1, 1, 2, 1, '��ȥ��ȥ�����Բ�����');

INSERT INTO tb_credit_record(id, user_id, action_type, credits, description) VALUES
(5, 2, 'ADD_COMMENT', 2, '���۲��͡�ѧһʳ��̽�걨�棡��'),
(6, 1, 'ADD_COMMENT', 2, '�ظ�����');

INSERT INTO tb_like (id, user_id, liked_id, type) VALUES
(1, 1, 10, 3), -- �û�1���޲�Ʒ'�̲����ֵ�'
(2, 2, 1, 1),  -- �û�2���޲���1
(3, 1, 2, 2);  -- �û�1��������2

UPDATE tb_dish SET liked = liked + 1 WHERE id = 10;
UPDATE tb_blog SET liked = liked + 1, comments = comments + 2 WHERE id = 1;
UPDATE tb_comment SET liked = liked + 1 WHERE id = 2; 

INSERT INTO tb_favorite (id, user_id, favorite_id, type) VALUES
(1, 1, 6, 2), -- �û�1�ղش���'CoCo����'
(2, 2, 1, 1); -- �û�2�ղز���1

INSERT INTO tb_notification (id, recipient_id, sender_id, type, target_id, target_content) VALUES
(1, 1, 2, 1, 1, '�ۣ��������úóԣ��´���Ҳȥѧһ������'), -- user2������user1�Ĳ���
(2, 2, 1, 2, 2, '��ȥ��ȥ�����Բ�����'),                 -- user1�ظ���user2������
(3, 1, 2, 3, 1, 'ѧһʳ��̽�걨�棡');                      -- user2������user1�Ĳ���


-- =============================================================================
-- ģ�� 5: ϵͳ������չʾ (System & Display)
-- =============================================================================

PRINT '���ڲ���: ģ�� 5 - ϵͳ������չʾ...';

INSERT INTO tb_banner (id, title, image_url, link_url, sort, status, start_time, end_time) VALUES
(1, '��ҵ�������Ż�', 'banner1.jpg', '/promotions/graduation', 1, 1, GETDATE(), DATEADD(month, 1, GETDATE())),
(2, 'ӭ������ʳ�Ƽ�', 'banner2.jpg', '/topics/welcome-new', 2, 1, GETDATE(), DATEADD(month, 1, GETDATE()));

INSERT INTO tb_notice (id, admin_id, title, content, type, status, publish_time) VALUES
(1, 1, '�����ļ�ʳ�ÿ���ʱ�������֪ͨ', '��λʦ����ΪӦ���ļ���Ϣ����7��1���𣬸�ʳ����͹�Ӧʱ���ӳ�����8�㣬���໥ת�档', 1, 1, GETDATE());

INSERT INTO tb_sensitive_word (id, word, category, level, admin_id) VALUES
(1, '���', '���', 1, 3),
(2, '��V', '���', 1, 3),
(3, 'ɵ��', '������', 2, 3);

INSERT INTO tb_system_config (id, config_key, config_name, config_value, remark) VALUES
(1, 'site_name', '��վ����', 'TjuFood �����ʳ', '��ʾ���������ǩҳ��ҳ��ͷ��'),
(2, 'default_user_avatar', '�û�Ĭ��ͷ��URL', 'default_avatar.png', '���û�ע��ʱʹ�õ�Ĭ��ͷ��');


    COMMIT TRANSACTION;
END TRY
BEGIN CATCH
    ROLLBACK TRANSACTION;
    PRINT '�ű�ִ�й����з����������в����ѻع���';
    THROW;
END CATCH;


PRINT '����ģ�����ݲ�����ɣ�';
