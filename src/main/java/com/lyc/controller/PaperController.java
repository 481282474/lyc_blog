package com.lyc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyc.common.GlobalException;
import com.lyc.common.Result;
import com.lyc.entity.Category;
import com.lyc.entity.Paper;
import com.lyc.service.CategoryService;
import com.lyc.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 文章管理
 * @author 刘怡畅
 * @date 2022/12/26 15:39
 */

@Slf4j
@RestController
@RequestMapping("/admin/paper")
public class PaperController {
    @Autowired
    private PaperService paperService;

    /**
     * 文章分页列表
     * @param categoryId
     * @param curPage
     * @return
     */
    @GetMapping("/list/{categoryId}/{curPage}")
    public Result list(@PathVariable Long categoryId,@PathVariable Integer curPage){
        log.info("categoryId:{},curPage:{}",categoryId,curPage);

        LambdaQueryWrapper<Paper> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Paper::getUpdateTime);
        Page<Paper> pageInfo = new Page<>(curPage,10);

        Page<Paper> result = paperService.page(pageInfo, wrapper);

        return Result.success(result);
    }

    /**
     * 从本地导入文章导入文章
     * @param path
     * @return
     */
    @PostMapping("/importFiles")
    public Result importFiles(String path) {
        try {
            paperService.importFiles(path);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(500,e.getMessage());
            //throw  new GlobalException(500,e.getMessage());
        }
    }

    /**
     * 在线添加添加文章
     * @param paper
     * @return
     */
    @PostMapping("/save")
    public Result save(@Valid Paper paper){
        try{
            paperService.updateById(paper);

            return Result.success(paper);
        }catch (Exception e){
            throw new GlobalException(500,e.getMessage());
        }
    }

    /**
     * 根据id获取文章信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result edit(@PathVariable String id){

        Paper paper = paperService.getById(id);
        return Result.success(paper);
    }

    @PostMapping("/delete/{paperId}")
    public Result delete(@PathVariable String paperId){
        try{

            paperService.removeById(paperId);
        }catch (Exception e){
            return Result.fail(500,"删除失败");
        }

        return Result.success();
    }
}
