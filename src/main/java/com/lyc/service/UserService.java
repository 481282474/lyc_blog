package com.lyc.service;

import com.lyc.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 蜡笔
* @description 针对表【user(员工信息)】的数据库操作Service
* @createDate 2022-12-22 10:09:39
*/
public interface UserService extends IService<User> {
    public User findByUsername(String userName);
}
