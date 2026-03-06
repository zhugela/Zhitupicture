package com.itheima.backend.model.vo;

import com.itheima.backend.model.entity.Space;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

@Data
public class SpaceVO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 名称
     */
    private String spaceName;

    /**
     * 空间级别
     */
    private Integer spaceLevel;
    /*
     * 空间图片的最大总大小
     */
    private Long totalSize;
    /*
     * 空间图片的数量
     */
    private Integer totalCount;
    /*
    创建时间
     */
    private Data createTime;

    /*
    编辑时间
     */
    private Data editTime;

    /*
    更新时间
     */
    private Data updateTime;

    /*
    创建用户信息
     */
    private UserVO user;

    /**
     * 序列号
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 封装类转对象
     */
    public static Space voToObj(SpaceVO spaceVO) {
        if (spaceVO == null) {
            return null;
        }
        Space space = new Space();
        BeanUtils.copyProperties(spaceVO, space);
        return space;
    }

    /*
     * 对象转封装类
     */
    public static SpaceVO objToVo(Space space) {
        if (space == null) {
            return null;
        }
        SpaceVO spaceVO = new SpaceVO();
        BeanUtils.copyProperties(space, spaceVO);
        return spaceVO;
    }
}
