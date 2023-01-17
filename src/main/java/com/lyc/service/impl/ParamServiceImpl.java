package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.common.GlobalException;
import com.lyc.common.vo.PerParamVo;
import com.lyc.entity.Param;
import com.lyc.service.ParamService;
import com.lyc.mapper.ParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【param(系统参数)】的数据库操作Service实现
* @createDate 2022-12-27 15:53:02
*/
@Service
public class ParamServiceImpl extends ServiceImpl<ParamMapper, Param> implements ParamService{

    @Autowired
    private ParamMapper paramMapper;

    //更新个人参数
    @Override
    public void upPerParam(PerParamVo perParam) {

    }
}




