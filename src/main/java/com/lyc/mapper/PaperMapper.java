package com.lyc.mapper;

import com.lyc.entity.Paper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 蜡笔
* @description 针对表【paper】的数据库操作Mapper
* @createDate 2022-12-25 18:54:01
* @Entity com/lyc.entity.Paper
*/
@Mapper
public interface PaperMapper extends BaseMapper<Paper> {

}




