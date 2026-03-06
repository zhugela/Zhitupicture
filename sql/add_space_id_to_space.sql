-- 空间表增加 space_id 字段（如果需要的话）
-- 注意：根据业务逻辑，space 表本身不需要 space_id 字段
-- 这个脚本仅供参考，请根据实际需求决定是否执行

-- 如果确实需要为 space 表添加 space_id 字段，取消下面的注释
/*
ALTER TABLE space 
ADD COLUMN space_id BIGINT COMMENT '空间 ID' 
AFTER userId;

CREATE INDEX idx_space_id ON space(space_id);
*/
