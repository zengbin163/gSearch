package com.gaojie.gsearch.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gaojie.gsearch.vo.entity.TUser;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zengbin
 * @since 2019-06-18
 */
public interface TUserDao extends BaseMapper<TUser> {
    List<TUser> getUserForTestMapperLocation();
    TUser getUserById(Integer id);
}
