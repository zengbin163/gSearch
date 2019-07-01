package com.gaojie.gsearch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaojie.gsearch.dao.TUserDao;
import com.gaojie.gsearch.vo.entity.TUser;

/**
 * @author zengbin
 * @Date 2019/6/8 14:27
 */
@RestController
public class TUserController {
    @Autowired
    private TUserDao userDao;

    @GetMapping("/user/get")
    public TUser get(@RequestParam("id") Integer id) {
        return userDao.getUserById(id);
    }
}
