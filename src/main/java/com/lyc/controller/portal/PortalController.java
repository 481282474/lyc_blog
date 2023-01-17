package com.lyc.controller.portal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyc.common.MarkdownUtil;
import com.lyc.common.Result;
import com.lyc.entity.AboutMe;
import com.lyc.entity.Paper;
import com.lyc.service.AboutMeService;
import com.lyc.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台控制器
 * @author 刘怡畅
 * @date 2022/12/29 16:45
 */

@Slf4j
@Controller
public class PortalController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private AboutMeService aboutMeService;

    private String render(Model model, String path) {

        model.addAttribute("menu", path.substring(path.indexOf("/") + 1));
        return path;
    }

    /**
     * 首页列表
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "/index.html"})
    public String index(Model model) throws Exception {
        LambdaQueryWrapper<Paper> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Paper::getUpdateTime);

        //状态为1，显示该文章
        wrapper.eq(Paper::getStatus,1);
        Page<Paper> pageInfo = new Page<>(1,10);

        Page<Paper> result = paperService.page(pageInfo, wrapper);

        model.addAttribute("pageInfo", result);

        return render(model, "portal/index");
    }


    /**
     * 查看文章详情
     * @param paperId 文章id
     * @param model 视图模型
     * @return
     */
    @GetMapping("/detail/{paperId}")
    public String detail(@PathVariable Long paperId,Model model){

        Paper paper = paperService.getById(paperId);
        model.addAttribute("paper",paper);
        //连带格式传入前端
        String content = MarkdownUtil.md2html(paper.getContent());
        model.addAttribute("content", content);
        return render(model,"portal/detail");
    }

    /**
     * 关于作者的信息
     */
    @GetMapping("/aboutMe")
    public String aboutMe(Model model){
        AboutMe aboutMe = aboutMeService.getById(1);
        model.addAttribute("aboutMe",aboutMe);
        return render(model,"portal/aboutMe");
    }

}
