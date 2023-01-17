package com.lyc.mapper;

import com.lyc.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 蜡笔
* @description 针对表【user(员工信息)】的数据库操作Mapper
* @createDate 2022-12-22 10:09:39
* @Entity generator.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




