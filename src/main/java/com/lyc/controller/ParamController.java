package com.lyc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyc.common.GlobalException;
import com.lyc.common.Result;
import com.lyc.common.vo.PerParamVo;
import com.lyc.entity.Param;
import com.lyc.service.ParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 参数控制器，包括个人参数和全局参数
 * @author 刘怡畅
 * @date 2022/12/29 20:22
 */

@Slf4j
@RestController
@RequestMapping("/admin/param")
public class ParamController {

    @Autowired
    private ParamService paramService;

    /**
     * 参数列表
     * @return
     */
    @GetMapping("/list/{type}")
    public Result paramList(@PathVariable Integer type) {
        try {
            LambdaQueryWrapper<Param> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Param::getType,type);

            List<Param> list = paramService.list(wrapper);
            return Result.success(list);
        } catch (Exception e) {
            throw  new GlobalException(500,e.getMessage());
        }
    }

    /**
     * 更新全局参数中的数据
     * @param value
     * @param pk 该记录的id
     * @return
     */
    @PostMapping("/updateSysParam")
    public Result updateSysParam(Integer pk,String value){
        try{
            log.info("value:{},id:{}",value,pk);
            Param param = paramService.getById(pk);
            param.setParamValue(value);

            paramService.updateById(param);
            return Result.success();
        }catch (Exception e){
            throw  new GlobalException(500,e.getMessage());
        }
    }

    /**
     * 修改个人参数
     * @param perParam 自定义实体
     */
    @PostMapping("/updatePerParam")
    public void updatePerParam(PerParamVo perParam){
        try{

            paramService.upPerParam(perParam);
        }catch (Exception e){
            throw new GlobalException(500,e.getMessage());
        }

    }


}
