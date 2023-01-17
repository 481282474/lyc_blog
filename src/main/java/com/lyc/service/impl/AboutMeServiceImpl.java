package com.lyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.entity.AboutMe;
import com.lyc.service.AboutMeService;
import com.lyc.mapper.AboutMeMapper;
import org.springframework.stereotype.Service;

/**
* @author 蜡笔
* @description 针对表【about_me】的数据库操作Service实现
* @createDate 2022-12-29 17:12:50
*/
@Service
public class AboutMeServiceImpl extends ServiceImpl<AboutMeMapper, AboutMe>
    implements AboutMeService{

}




