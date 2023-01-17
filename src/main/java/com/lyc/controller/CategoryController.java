package com.lyc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyc.common.Result;
import com.lyc.entity.Category;
import com.lyc.entity.Paper;
import com.lyc.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类控制器
 * @author 刘怡畅
 * @date 2022/12/27 12:49
 */

@Slf4j
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 文章分页列表
     * @param curPage
     * @return
     */
    @GetMapping("/list/{curPage}")
    public Result list(@PathVariable Integer curPage){
        log.info("curPage:{}",curPage);

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Category::getSort);
        Page<Category> pageInfo = new Page<>(curPage,10);

        Page<Category> result = categoryService.page(pageInfo, wrapper);

        return Result.success(result);
    }

    /**
     * 分类集合
     * @return
     */
    @GetMapping("/listAll")
    public Result listAll(){
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }


}
