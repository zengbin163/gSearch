package com.famiao.search.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.famiao.search.dao.TUserDao;
import com.famiao.search.service.user.UserService;
import com.famiao.search.vo.entity.TUser;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private TUserDao userDao;

    @Override
    public IPage<TUser> getUserForTestMapperLocation(Page<TUser> page) {
        return this.userDao.getUserForTestMapperLocation(page);
    }

    @Override
    public TUser getUserByUUId(String uuid) {
        return this.userDao.getUserByUUId(uuid);
    }

}
