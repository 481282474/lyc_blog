package com.lyc.mapper;

import com.lyc.entity.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 蜡笔
* @description 针对表【param(系统参数)】的数据库操作Mapper
* @createDate 2022-12-27 15:53:02
* @Entity com.lyc.entity.Param
*/

@Mapper
public interface ParamMapper extends BaseMapper<Param> {

}




