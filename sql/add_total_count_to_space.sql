-- 空间表增加 totalCount 字段
-- 用于记录当前空间下的图片总数

ALTER TABLE space 
ADD COLUMN space_id INT DEFAULT 0 COMMENT '当前空间下的图片总数';


-- 注意：不需要为 space 表添加 space_id 字段
-- space 表本身代表空间，不需要引用自己
ALTER TABLE picture
    CHANGE COLUMN spaceId space_id BIGINT;

select id, user_id, space_name, space_level, space_type, max_size, max_count, total_size, total_count
from space
where id = 4;

alter table space add column space_type tinyint default 0 not null comment '空间类型：0-私有空间 1-团队空间';

ALTER TABLE picture
    ADD COLUMN picColor varchar(16) null comment '图片主色调';
