-- 空间表增加 totalCount 字段
-- 用于记录当前空间下的图片总数

ALTER TABLE space 
ADD COLUMN totalCount INT DEFAULT 0 COMMENT '当前空间下的图片总数' 
AFTER totalSize;

-- 注意：不需要为 space 表添加 space_id 字段
-- space 表本身代表空间，不需要引用自己
