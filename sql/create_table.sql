-- 创建库
create database if not exists picture;

use picture;


-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin',
    editTime     datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    UNIQUE KEY uk_userAccount (userAccount),
    INDEX idx_userName (userName)
    ) comment '用户' collate = utf8mb4_unicode_ci;

-- 图片表
create table if not exists picture
(
    id           bigint auto_increment comment 'id' primary key,
    url          varchar(512)                       not null comment '图片 url',
    name         varchar(128)                       not null comment '图片名称',
    introduction varchar(512)                       null comment '简介',
    category     varchar(64)                        null comment '分类',
    tags         varchar(512)                      null comment '标签（JSON 数组）',
    picSize      bigint                             null comment '图片体积',
    picWidth     int                                null comment '图片宽度',
    picHeight    int                                null comment '图片高度',
    picScale     double                             null comment '图片宽高比例',
    picFormat    varchar(32)                        null comment '图片格式',
    userId       bigint                             not null comment '创建用户 id',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    editTime     datetime default CURRENT_TIMESTAMP not null comment '编辑时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    INDEX idx_name (name),                 -- 提升基于图片名称的查询性能
    INDEX idx_introduction (introduction), -- 用于模糊搜索图片简介
    INDEX idx_category (category),         -- 提升基于分类的查询性能
    INDEX idx_tags (tags),                 -- 提升基于标签的查询性能
    INDEX idx_userId (userId)              -- 提升基于用户 ID 的查询性能
) comment '图片' collate = utf8mb4_unicode_ci;

ALTER TABLE picture
    -- 添加新列
    ADD COLUMN reviewStatus INT DEFAULT 0 NOT NULL COMMENT '审核状态：0-待审核; 1-通过; 2-拒绝',
    ADD COLUMN reviewMessage VARCHAR(512) NULL COMMENT '审核信息',
    ADD COLUMN reviewerId BIGINT NULL COMMENT '审核人 ID',
    ADD COLUMN reviewTime DATETIME NULL COMMENT '审核时间';

-- 创建基于 reviewStatus 列的索引
CREATE INDEX idx_reviewStatus ON picture (reviewStatus);
ALTER TABLE picture
    -- 添加新列
    ADD COLUMN thumbnailUrl varchar(512) NULL COMMENT '缩略图 url';

create table if not exists space
(
    -- 表的主键 ID，自增列
    -- bigint: 数据类型，支持较大的整数值（范围：-9223372036854775808 到 9223372036854775807）
    -- auto_increment: 自动递增，每次插入新记录时自动生成唯一值
    -- primary key: 主键约束，确保列值唯一且非空
    -- comment 'id': 列注释，说明该列的用途
    id      bigint auto_increment primary key comment 'id',
    -- 空间名称字段，用于存储图片空间的名称标识
    -- varchar(128): 可变长度字符串，最大 128 个字符
    -- not null: 非空约束，确保每个空间都有名称
    -- comment '空间名称': 列注释，说明该列存储空间名称信息
    spaceName varchar(128) not null comment '空间名称',

    spaceLevel int default 0 not null comment '空间等级',

    maxSize bigint default 0 not null comment '空间图片的最大总大小',

    maxCount int default 0 not null comment '空间的最大图片数量',

    totalSize bigint default 0 not null comment '当前空间下的图片总大小',

    userId bigint not null comment '创建用户Id',

    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',

    editTime datetime default CURRENT_TIMESTAMP not null comment '编辑时间',

    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',

    isDelete tinyint default 0 not null comment '是否删除',
    -- 添加索引
    INDEX idx_spaceName (spaceName),
    INDEX idx_userId (userId),
    INDEX idx_spaceLevel (spaceLevel)



)COMMENT '图片空间' collate = utf8mb4_unicode_ci;

-- 添加新列
ALTER TABLE space
    ADD COLUMN spaceId bigint NULL COMMENT '空间 id';

-- 创建索引
CREATE INDEX idx_spaceId ON space (spaceId);