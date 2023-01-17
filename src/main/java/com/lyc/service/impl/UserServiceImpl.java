package com.lyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.entity.User;
import com.lyc.service.UserService;
import com.lyc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【user(员工信息)】的数据库操作Service实现
* @createDate 2022-12-22 10:09:39
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 查询该账号是否存在
     * @param userName
     * @return 查询结果
     */
    @Override
    public User findByUsername(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,userName);
        return userMapper.selectOne(wrapper);
    }
}




