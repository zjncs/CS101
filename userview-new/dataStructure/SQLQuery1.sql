-- 1.1 tb_user - ��ͨ�û���
CREATE TABLE tb_user (
    id BIGINT PRIMARY KEY IDENTITY(1,1),                -- �û�ID
    password NVARCHAR(100) NOT NULL,                    -- ���� (���ܴ洢)
    email NVARCHAR(100) UNIQUE NOT NULL,               -- ���� (���ڵ�¼����֤)
    phone VARCHAR(20) NULL,                            -- �ֻ���
    nickname NVARCHAR(50) NOT NULL,                      -- �ǳ�
    icon NVARCHAR(255) NULL,                           -- ͷ��URL
    gender TINYINT NOT NULL DEFAULT 2,                   -- �Ա� (0=Ů, 1=��, 2=δ֪)
    birthday DATE NULL,                                -- ����
    bio NVARCHAR(300) NULL,                            -- ���˼��
    campus NVARCHAR(50) NOT NULL,                        -- ����У��
    credits INT NOT NULL DEFAULT 0,                      -- ����
    level INT NOT NULL DEFAULT 1,                        -- �û��ȼ�
    status TINYINT NOT NULL DEFAULT 0,                   -- ״̬ (0=����, 1=����)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),    -- ����ʱ��
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()     -- ����ʱ��
);
GO

-- 1.2 tb_admin - ����Ա��
CREATE TABLE tb_admin (
    id BIGINT PRIMARY KEY IDENTITY(1,1),                -- ����ԱID
    username NVARCHAR(50) NOT NULL UNIQUE,             -- ����Ա��¼�˺�
    password NVARCHAR(100) NOT NULL,                    -- ����Ա���� (���ܴ洢)
    name NVARCHAR(50) NOT NULL,                        -- ��ʵ������ְλ
    status TINYINT NOT NULL DEFAULT 0,                   -- ״̬ (0=����, 1=����)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 1.3 tb_role - ��ɫ��
CREATE TABLE tb_role (
    id BIGINT PRIMARY KEY IDENTITY(1,1),                -- ��ɫID
    name NVARCHAR(50) NOT NULL UNIQUE,                 -- ��ɫ����
    description NVARCHAR(255) NULL,                    -- ��ɫ����
    status TINYINT NOT NULL DEFAULT 0,                   -- ״̬ (0=����, 1=����)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 1.4 tb_admin_role - ����Ա��ɫ������
CREATE TABLE tb_admin_role (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    admin_id BIGINT NOT NULL,                            -- ����ԱID
    role_id BIGINT NOT NULL,                             -- ��ɫID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_admin_role UNIQUE (admin_id, role_id),
    CONSTRAINT FK_adminrole_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id) ON DELETE CASCADE,
    CONSTRAINT FK_adminrole_role FOREIGN KEY (role_id) REFERENCES tb_role(id) ON DELETE CASCADE
);
GO

-- 1.5 tb_permission - Ȩ�ޱ�
CREATE TABLE tb_permission (
    id BIGINT PRIMARY KEY IDENTITY(1,1),                -- Ȩ��ID
    name NVARCHAR(50) NOT NULL,                        -- Ȩ������ (��: "������Ʒ")
    permission_key VARCHAR(100) NOT NULL UNIQUE,       -- Ȩ�ޱ�ʶ (��: "dish:create")
    description NVARCHAR(255) NULL,                    -- Ȩ������
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 1.6 tb_role_permission - ��ɫȨ�޹�����
CREATE TABLE tb_role_permission (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    role_id BIGINT NOT NULL,                             -- ��ɫID
    permission_id BIGINT NOT NULL,                       -- Ȩ��ID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_role_permission UNIQUE (role_id, permission_id),
    CONSTRAINT FK_rolepermission_role FOREIGN KEY (role_id) REFERENCES tb_role(id) ON DELETE CASCADE,
    CONSTRAINT FK_rolepermission_permission FOREIGN KEY (permission_id) REFERENCES tb_permission(id) ON DELETE CASCADE
);
GO
-- 2.1 tb_campus - У����
CREATE TABLE tb_campus (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- У��ID
    name NVARCHAR(50) NOT NULL UNIQUE,         -- У������
    address NVARCHAR(255) NULL,                -- ��ַ
    x FLOAT NULL,                              -- ����
    y FLOAT NULL                               -- γ��
);
GO

-- 2.2 tb_canteen_type - ʳ�����ͱ�
CREATE TABLE tb_canteen_type (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ����ID
    name NVARCHAR(50) NOT NULL UNIQUE,         -- ��������
    icon NVARCHAR(255) NULL,                   -- ͼ��URL
    sort INT NOT NULL DEFAULT 0                -- ����ֵ
);
GO

-- 2.3 tb_canteen - ʳ�ñ�
CREATE TABLE tb_canteen (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ʳ��ID
    name NVARCHAR(100) NOT NULL,               -- ʳ������
    campus_id BIGINT NOT NULL,                   -- ����У��ID
    type_id BIGINT NOT NULL,                     -- ʳ������ID
    address NVARCHAR(255) NOT NULL,            -- ��ϸλ��
    floor NVARCHAR(20) NULL,                   -- ¥����Ϣ
    open_hours NVARCHAR(255) NULL,             -- Ӫҵʱ��
    avg_price INT NULL,                        -- �˾��۸�
    introduction NVARCHAR(500) NULL,           -- ʳ�ý���
    score FLOAT NOT NULL DEFAULT 0,              -- �ۺ�����
    taste_score FLOAT NOT NULL DEFAULT 0,        -- ��ζ����
    environment_score FLOAT NOT NULL DEFAULT 0,  -- ��������
    service_score FLOAT NOT NULL DEFAULT 0,      -- ��������
    liked INT NOT NULL DEFAULT 0,                -- ������
    comments INT NOT NULL DEFAULT 0,             -- ������
    open_status TINYINT NOT NULL DEFAULT 1,      -- Ӫҵ״̬ (0=��Ϣ, 1=Ӫҵ)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_canteen_campus FOREIGN KEY (campus_id) REFERENCES tb_campus(id),
    CONSTRAINT FK_canteen_type FOREIGN KEY (type_id) REFERENCES tb_canteen_type(id)
);
GO

-- 2.4 tb_stall_type - �������ͱ�
CREATE TABLE tb_stall_type (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ����ID
    name NVARCHAR(50) NOT NULL UNIQUE,         -- ��������
    icon NVARCHAR(255) NULL,                   -- ͼ��URL
    sort INT NOT NULL DEFAULT 0                -- ����ֵ
);
GO

-- 2.5 tb_stall - ���ڱ�
CREATE TABLE tb_stall (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ����ID
    name NVARCHAR(100) NOT NULL,               -- ��������
    canteen_id BIGINT NOT NULL,                  -- ����ʳ��ID
    type_id BIGINT NOT NULL,                     -- ��������ID
    location NVARCHAR(50) NULL,                -- ����λ�ñ��
    introduction NVARCHAR(500) NULL,           -- ���ڽ���
    open_hours NVARCHAR(255) NULL,             -- Ӫҵʱ��
    score FLOAT NOT NULL DEFAULT 0,              -- �ۺ�����
    taste_score FLOAT NOT NULL DEFAULT 0,        -- ��ζ����
    price_score FLOAT NOT NULL DEFAULT 0,        -- �Լ۱�����
    comments INT NOT NULL DEFAULT 0,             -- ������
    open_status TINYINT NOT NULL DEFAULT 1,      -- Ӫҵ״̬ (0=��Ϣ, 1=Ӫҵ)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_stall_canteen FOREIGN KEY (canteen_id) REFERENCES tb_canteen(id) ON DELETE CASCADE,
    CONSTRAINT FK_stall_type FOREIGN KEY (type_id) REFERENCES tb_stall_type(id)
);
GO

-- 2.6 tb_dish - ��Ʒ��
CREATE TABLE tb_dish (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ��ƷID
    name NVARCHAR(100) NOT NULL,               -- ��Ʒ����
    stall_id BIGINT NOT NULL,                    -- ��������ID
    category NVARCHAR(50) NULL,                -- ��Ʒ����
    price DECIMAL(10, 2) NOT NULL,             -- �۸�
    description NVARCHAR(500) NULL,            -- ��Ʒ����
    score FLOAT NOT NULL DEFAULT 0,              -- ����
    comments INT NOT NULL DEFAULT 0,             -- �����ἰ����
    status TINYINT NOT NULL DEFAULT 1,           -- ״̬ (0=�¼�, 1=�ϼ�)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_dish_stall FOREIGN KEY (stall_id) REFERENCES tb_stall(id) ON DELETE CASCADE
);
GO
-- 3.1 tb_blog - ����/�ʼǱ�
CREATE TABLE tb_blog (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ����ID
    user_id BIGINT NOT NULL,                     -- ����ID
    title NVARCHAR(200) NOT NULL,              -- ����
    content NVARCHAR(2000) NOT NULL,           -- ��������
    liked INT NOT NULL DEFAULT 0,                -- ������
    comments INT NOT NULL DEFAULT 0,             -- ������
    status TINYINT NOT NULL DEFAULT 0,           -- ״̬ (0=����, 1=����, 2=����Ա����)
    is_top TINYINT NOT NULL DEFAULT 0,           -- �Ƿ��ö� (0=��, 1=��)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_blog_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO

-- 3.2 tb_review - ���۱�
CREATE TABLE tb_review (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ����ID
    user_id BIGINT NOT NULL,                     -- �û�ID
    content NVARCHAR(1024) NOT NULL,           -- ��������
    target_id BIGINT NOT NULL,                   -- �����۶���ID
    target_type TINYINT NOT NULL,                -- �����۶������� (1=ʳ��, 2=����, 3=��Ʒ)
    overall_score INT NOT NULL CHECK (overall_score BETWEEN 1 AND 5), -- ��������
    taste_score INT NULL CHECK (taste_score BETWEEN 1 AND 5), -- ��ζ����
    environment_score INT NULL CHECK (environment_score BETWEEN 1 AND 5), -- ��������
    service_score INT NULL CHECK (service_score BETWEEN 1 AND 5), -- ��������
    price_score INT NULL CHECK (price_score BETWEEN 1 AND 5), -- �Լ۱�����
    liked INT NOT NULL DEFAULT 0,                -- ������
    status TINYINT NOT NULL DEFAULT 0,           -- ״̬ (0=�����, 1=��ͨ��, 2=�Ѿܾ�)
    auditor_id BIGINT NULL,                      -- ��˹���ԱID
    audit_time DATETIME2 NULL,                   -- ���ʱ��
    audit_note NVARCHAR(255) NULL,               -- ��˱�ע
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_review_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT FK_review_auditor FOREIGN KEY (auditor_id) REFERENCES tb_admin(id)
);
GO

-- 3.3 tb_comment - ���۱�
CREATE TABLE tb_comment (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ����ID
    user_id BIGINT NOT NULL,                     -- �û�ID
    target_id BIGINT NOT NULL,                   -- �����۶���ID (���ͻ�����)
    target_type TINYINT NOT NULL,                -- �����۶������� (1=����, 2=����)
    parent_id BIGINT NULL,                       -- ������ID (����¥��¥�ظ�)
    content NVARCHAR(255) NOT NULL,            -- ��������
    liked INT NOT NULL DEFAULT 0,                -- ������
    status TINYINT NOT NULL DEFAULT 0,           -- ״̬ (0=�����, 1=��ͨ��, 2=�Ѿܾ�)
    auditor_id BIGINT NULL,                      -- ��˹���ԱID
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_comment_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT FK_comment_parent FOREIGN KEY (parent_id) REFERENCES tb_comment(id),
    CONSTRAINT FK_comment_auditor FOREIGN KEY (auditor_id) REFERENCES tb_admin(id)
);
GO

-- 3.4 tb_like - ���ޱ�
CREATE TABLE tb_like (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ����ID
    user_id BIGINT NOT NULL,                     -- �û�ID
    liked_id BIGINT NOT NULL,                    -- �����޶���ID
    type TINYINT NOT NULL,                       -- ���޶������� (1=����, 2=����, 3=����)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_like UNIQUE (user_id, liked_id, type),
    CONSTRAINT FK_like_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO

-- 3.5 tb_favorite - �ղر�
CREATE TABLE tb_favorite (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- �ղ�ID
    user_id BIGINT NOT NULL,                     -- �û�ID
    favorite_id BIGINT NOT NULL,                 -- ���ղض���ID
    type TINYINT NOT NULL,                       -- �ղض������� (1=����, 2=ʳ��, 3=����, 4=��Ʒ)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_favorite UNIQUE (user_id, favorite_id, type),
    CONSTRAINT FK_favorite_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO

-- 3.6 tb_search_history - ������ʷ��
CREATE TABLE tb_search_history (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ��ʷID
    user_id BIGINT NOT NULL,                     -- �û�ID
    keyword NVARCHAR(100) NOT NULL,              -- �ؼ���
    create_time DATETIME2 NOT NULL DEFAULT GETDATE() -- ����ʱ��
);
GO

-- 3.7 tb_report - �ٱ���
CREATE TABLE tb_report (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- �ٱ�ID
    user_id BIGINT NOT NULL,                     -- �ٱ��û�ID
    target_id BIGINT NOT NULL,                   -- �ٱ�Ŀ��ID
    type TINYINT NOT NULL,                       -- �ٱ����� (0=����, 1=����, 2=����, 3=�û�)
    reason NVARCHAR(255) NOT NULL,              -- �ٱ�ԭ��
    description NVARCHAR(500) NULL,            -- �ٱ�����
    status TINYINT NOT NULL DEFAULT 0,           -- ״̬ (0=δ����, 1=�Ѵ���, 2=�Ѳ���)
    handle_admin_id BIGINT NULL,                 -- �������ԱID
    handle_note NVARCHAR(255) NULL,              -- ����ע
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_report_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT FK_report_admin FOREIGN KEY (handle_admin_id) REFERENCES tb_admin(id)
);
GO
-- 4.1 tb_credit_record - ���ּ�¼��
CREATE TABLE tb_credit_record (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ��¼ID
    user_id BIGINT NOT NULL,                     -- �û�ID
    type NVARCHAR(50) NOT NULL,                -- �������� (��: 'ǩ��', '����', '�һ�')
    credits INT NOT NULL,                        -- ���ֱ䶯ֵ (����)
    description NVARCHAR(255) NULL,            -- ��������
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_creditrecord_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO

-- 4.2 tb_sign - ǩ����
CREATE TABLE tb_sign (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ǩ��ID
    user_id BIGINT NOT NULL,                     -- �û�ID
    date DATE NOT NULL,                        -- ǩ������
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_sign UNIQUE (user_id, date)
);
GO

-- 4.3 tb_voucher - �Ż�ȯģ���
CREATE TABLE tb_voucher (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- �Ż�ȯID
    title NVARCHAR(100) NOT NULL,              -- �Ż�ȯ����
    description NVARCHAR(255) NULL,            -- ����
    type TINYINT NOT NULL DEFAULT 0,             -- ���� (0=����ȯ, 1=�ۿ�ȯ)
    price DECIMAL(10, 2) NULL,                 -- �Żݽ��
    discount DECIMAL(5, 2) NULL,               -- �ۿ���
    min_amount DECIMAL(10, 2) NOT NULL DEFAULT 0, -- ������ѽ��
    canteen_id BIGINT NULL,                      -- �޶�ʳ��ID
    start_time DATETIME2 NOT NULL,               -- ��Чʱ��
    end_time DATETIME2 NOT NULL,                 -- ����ʱ��
    stock INT NOT NULL DEFAULT 0,                -- ���
    required_credits INT NOT NULL DEFAULT 0,     -- �һ��������
    status TINYINT NOT NULL DEFAULT 0,           -- ״̬ (0=δ��ʼ, 1=������, 2=�ѽ���)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 4.4 tb_user_voucher - �û��Ż�ȯ��
CREATE TABLE tb_user_voucher (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- �û�ȯID
    user_id BIGINT NOT NULL,                     -- �û�ID
    voucher_id BIGINT NOT NULL,                  -- �Ż�ȯID
    status TINYINT NOT NULL DEFAULT 0,           -- ״̬ (0=δʹ��, 1=��ʹ��, 2=�ѹ���)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(), -- ��ȡʱ��
    use_time DATETIME2 NULL,                     -- ʹ��ʱ��
    CONSTRAINT FK_uservoucher_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT FK_uservoucher_voucher FOREIGN KEY (voucher_id) REFERENCES tb_voucher(id)
);
GO
-- 5.1 tb_banner - �ֲ�ͼ��
CREATE TABLE tb_banner (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- �ֲ�ͼID
    title NVARCHAR(100) NOT NULL,              -- ����
    image_url NVARCHAR(255) NOT NULL,          -- ͼƬURL
    link_url NVARCHAR(255) NULL,               -- ��ת����
    sort INT NOT NULL DEFAULT 0,                 -- ����ֵ
    status TINYINT NOT NULL DEFAULT 0,           -- ״̬ (0=����, 1=����)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 5.2 tb_notice - ϵͳ�����
CREATE TABLE tb_notice (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ����ID
    admin_id BIGINT NOT NULL,                    -- ��������ԱID
    title NVARCHAR(100) NOT NULL,              -- �������
    content NVARCHAR(MAX) NOT NULL,            -- ��������
    type TINYINT NOT NULL DEFAULT 0,             -- �������� (0=��ͨ, 1=��Ҫ)
    status TINYINT NOT NULL DEFAULT 0,           -- ״̬ (0=�ݸ�, 1=�ѷ���)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_notice_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id)
);
GO

-- 5.3 tb_log - ϵͳ��־��
CREATE TABLE tb_log (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ��־ID
    admin_id BIGINT NULL,                        -- �����Ĺ���ԱID
    operation NVARCHAR(100) NOT NULL,          -- ��������
    method NVARCHAR(255) NULL,                 -- ���󷽷�
    params NVARCHAR(1000) NULL,                -- �������
    ip VARCHAR(50) NULL,                         -- IP��ַ
    create_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO

-- 5.4 tb_file - �ļ��� (����)
CREATE TABLE tb_file (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- �ļ�ID
    uploader_id BIGINT NOT NULL,                 -- �ϴ���ID
    uploader_type TINYINT NOT NULL,              -- �ϴ������� (1=�û�, 2=����Ա)
    target_id BIGINT NULL,                       -- ��������ID
    target_type TINYINT NULL,                    -- ������������
    name NVARCHAR(255) NOT NULL,               -- ϵͳ���ɵ��ļ���
    original_name NVARCHAR(255) NOT NULL,      -- ԭʼ�ļ���
    url NVARCHAR(255) NOT NULL,                -- �ļ�URL
    type VARCHAR(50) NOT NULL,                   -- �ļ�MIME����
    size INT NOT NULL,                           -- �ļ���С(�ֽ�)
    status TINYINT NOT NULL DEFAULT 0,           -- ״̬ (0=����, 1=��ɾ��)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE()
);
GO
-- 6.1 tb_ranking - ���а��
CREATE TABLE tb_ranking (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ����ID
    type TINYINT NOT NULL,                       -- �������� (0=��Ʒ, 1=ʳ��, 2=����, 3=�û�����)
    target_id BIGINT NOT NULL,                   -- Ŀ��ID
    score FLOAT NOT NULL,                        -- ����/��ֵ
    rank INT NOT NULL,                           -- ����
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_ranking UNIQUE (type, target_id)
);
GO

-- 6.2 tb_statistics - ͳ�����ݱ�
CREATE TABLE tb_statistics (
    id BIGINT PRIMARY KEY IDENTITY(1,1),        -- ͳ��ID
    type TINYINT NOT NULL,                       -- ͳ������ (0=�û���Ծ��, 1=���ֲַ�)
    target_id BIGINT NULL,                       -- Ŀ��ID (��ʳ��ID)
    period VARCHAR(20) NOT NULL,               -- ͳ������ (day/week/month/year)
    date DATE NOT NULL,                        -- ͳ������
    data NVARCHAR(MAX) NOT NULL,               -- ͳ������(JSON�ַ���)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_statistics UNIQUE (type, target_id, period, date)
);
GO