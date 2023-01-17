package com.lyc.mapper;

import com.lyc.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 蜡笔
* @description 针对表【category】的数据库操作Mapper
* @createDate 2022-12-25 19:23:12
* @Entity com.lyc.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




