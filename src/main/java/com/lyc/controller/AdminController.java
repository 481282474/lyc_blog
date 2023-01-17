package com.lyc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyc.common.GlobalException;
import com.lyc.common.Result;
import com.lyc.common.vo.LoginVo;
import com.lyc.entity.Param;
import com.lyc.entity.User;
import com.lyc.service.CategoryService;
import com.lyc.service.PaperService;
import com.lyc.service.ParamService;
import com.lyc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘怡畅
 * @date 2022/12/22 12:55
 */

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ParamService paramService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 管理员登录
     * @param loginVo 登录信息
     * @param session
     * @return
     * @throws GlobalException
     */
    @PostMapping("/login")
    public Result login(@Valid LoginVo loginVo, HttpSession session) throws GlobalException {
        log.info("输出session：{}", session.toString());
        try {

            User user = this.userService.findByUsername(loginVo.getUsername());

            if (user == null) {
                throw new GlobalException(403,"用户名不存在");
            }

            //将密码进行md5加密后，再判断是否正确
            String md5Password= DigestUtils.md5DigestAsHex(loginVo.getPassword().getBytes());
            if (!user.getPassword().equals(md5Password)) {
                throw new GlobalException(403,"密码不正确");
            }

            if (user.getStatus() == 0) {
                throw new GlobalException(403,"该用户被禁用");
            }
            //清除密码记录
            user.setPassword(null);

            session.setAttribute("loginUser",user);
            log.info("输出loginUser：{}", session.getAttribute("loginUser"));

            return Result.success("/admin/index");

        } catch (GlobalException e) {
            return Result.fail(500,"用户名或密码错误！");
            //throw new GlobalException(500,e.getMessage());
        }
    }

    @GetMapping("/index/basicInfo")
    public Result basicInfo(HttpSession session) {
        try {
            //判断是否登录
            User user = (User) session.getAttribute("loginUser");

            Map<String,Object> map = new HashMap<>();
            map.put("user",user);

            return Result.success(map);
        } catch (Exception e) {
            throw new GlobalException(500,e.getMessage());
        }
    }


    /**
     * 指标数据
     * @return
     */
    @GetMapping("/index/indexData")
    public Result indexData() {

        try {
            //文章数量
            Long paperCount = paperService.count();
            Long categoryCount = categoryService.count();
            //已发布文章数量
            Long releaseCount=paperService.releaseCount();


            Map<String,Long> map = new HashMap<>(3);
            map.put("paperCount",paperCount);
            map.put("categoryCount",categoryCount);
            map.put("releaseCount",releaseCount);

            return Result.success(map);
        } catch (Exception e) {
            throw new GlobalException(500,e.getMessage());
        }
    }

    /**
     * 系统参数
     * @return
     */
    @GetMapping("/index/sysParamData")
    public Result sysParamData() {
        try {
            LambdaQueryWrapper<Param> wrapper = new LambdaQueryWrapper<>();
            //type=1代表全局参数
            wrapper.eq(Param::getType,1);
            List<Param> list = paramService.list(wrapper);
            return Result.success(list);
        } catch (Exception e) {
            throw new GlobalException(500,e.getMessage());
        }
    }

}