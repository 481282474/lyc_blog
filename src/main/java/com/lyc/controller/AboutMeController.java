package com.lyc.controller;

import com.lyc.common.GlobalException;
import com.lyc.common.Result;
import com.lyc.entity.AboutMe;
import com.lyc.service.AboutMeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 刘怡畅
 * @date 2022/12/29 17:15
 */
@RestController
@RequestMapping("/admin/aboutMe")
public class AboutMeController {

    @Autowired
    private AboutMeService aboutMeService;

    /**
     * 获取表数据
     * @return
     */
    @GetMapping("/getAboutMe")
    public Result getAboutMe() {
        AboutMe aboutMe = aboutMeService.getById(1);
        return Result.success(aboutMe);
    }

    /**
     * 更新信息，@Valid注解用于判断传入的数据是否符合规范
     * @param aboutMe
     * @return
     */
    @PostMapping("/save")
    public Result save(@Valid AboutMe aboutMe) {
        try {
            aboutMeService.updateById(aboutMe);
            return Result.success();
        } catch (Exception e) {
            throw new GlobalException(500,e.getMessage());
        }
    }
}

