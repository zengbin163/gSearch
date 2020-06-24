package com.famiao.search.service.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.famiao.search.vo.entity.TUser;

public interface UserService {
    IPage<TUser> getUserForTestMapperLocation(Page<TUser> page);

    TUser getUserByUUId(String uuid);
}
