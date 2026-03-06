-- 图片表增加 spaceId 字段
-- 用于标识图片所属的空间，null 表示公共图库

ALTER TABLE picture 
ADD COLUMN space_id BIGINT COMMENT '空间 ID，null 表示公共图库' 


-- 为 space_id 字段添加索引以提高查询性能
CREATE INDEX idx_space_id ON picture(space_id);

-- 可选：创建联合索引（根据实际查询需求）
-- CREATE INDEX idx_space_id_review_status ON picture(space_id, review_status);
