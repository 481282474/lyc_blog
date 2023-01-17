package com.lyc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyc.entity.Paper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 蜡笔
* @description 针对表【paper】的数据库操作Service
* @createDate 2022-12-25 18:54:01
*/
public interface PaperService extends IService<Paper> {

    void importFiles(String path);

    Long releaseCount();
}
