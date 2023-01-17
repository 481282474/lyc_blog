package com.lyc.service;

import com.lyc.common.GlobalException;
import com.lyc.common.vo.PerParamVo;
import com.lyc.entity.Param;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【param(系统参数)】的数据库操作Service
* @createDate 2022-12-27 15:53:02
*/
public interface ParamService extends IService<Param> {

    void upPerParam(PerParamVo perParam);
}
