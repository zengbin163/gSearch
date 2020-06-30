package com.famiao.search.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.famiao.search.service.user.UserService;
import com.famiao.search.vo.entity.TUser;

/**
 * @author zengbin
 * @Date 2019/6/8 14:27
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/get")
    public TUser get(@RequestParam("uuid") String uuid) {
        return userService.getUserByUUId(uuid);
    }

    @GetMapping("/user/pageList")
    public IPage<TUser> pageList(@RequestParam("current") long current,@RequestParam("size") long size) {
        Page<TUser> page = new Page<TUser>();
        page.setCurrent(current);
        page.setSize(size);
        return userService.getUserForTestMapperLocation(page);
    }
}
