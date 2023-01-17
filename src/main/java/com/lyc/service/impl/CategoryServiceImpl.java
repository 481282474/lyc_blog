package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.entity.Category;
import com.lyc.service.CategoryService;
import com.lyc.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【category】的数据库操作Service实现
* @createDate 2022-12-25 19:23:12
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




